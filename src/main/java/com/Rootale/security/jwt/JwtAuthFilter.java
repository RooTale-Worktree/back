
package com.Rootale.security.jwt;

import com.Rootale.member.entity.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    // 인증이 필요없는 경로
    private static final String[] WHITELIST = {
            "/user/login",
            "/user/refresh",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**",
            "/error"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();

        // OPTIONS (CORS preflight)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // Whitelist 체크
        for (String pattern : WHITELIST) {
            if (PATH_MATCHER.match(pattern, uri)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        try {
            String token = resolveToken(req);

            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.isValid(token)) {
                    // JWT에서 사용자 정보 추출
                    String subject = jwtUtil.extractSubject(token);
                    String username = jwtUtil.extractClaim(token, "username");
                    String userIdStr = jwtUtil.extractClaim(token, "userId");

                    Long userId = userIdStr != null ? Long.parseLong(userIdStr) : Long.parseLong(subject);

                    // CustomUser 객체 생성
                    CustomUser customUser = new CustomUser(
                            userId,
                            username != null ? username : subject,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );

                    // SecurityContext에 인증 정보 저장
                    var auth = new UsernamePasswordAuthenticationToken(
                            customUser,
                            null,
                            customUser.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.debug("✅ JWT authentication successful - userId: {}", userId);
                }
            }
        } catch (Exception e) {
            log.debug("⚠️ JWT validation failed: {}", e.getMessage());
        }

        chain.doFilter(req, res);
    }

    private String resolveToken(HttpServletRequest req) {
        // Authorization 헤더에서 Bearer 토큰 추출
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}