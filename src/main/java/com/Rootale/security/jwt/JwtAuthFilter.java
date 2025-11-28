
package com.Rootale.security.jwt;

import com.Rootale.member.entity.CustomUser;
import com.Rootale.member.entity.User;
import com.Rootale.member.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;  // ⭐ JwtUtil 대신 JwtTokenService 사용
    private final UserRepository userRepository;
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

//        // OPTIONS (CORS preflight)
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            return true;
//        }

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

        String token = resolveToken(req);

        if (token == null) {
            log.debug("🔒 No JWT token found in request");
            chain.doFilter(req, res);
            return;
        }

        try {
            // ⭐ JwtTokenService로 토큰 검증
            if (!jwtTokenService.validateToken(token)) {
                log.warn("⚠️ Invalid JWT token");
                chain.doFilter(req, res);
                return;
            }

            // ⭐ 토큰에서 userId 추출
            Long userId = jwtTokenService.extractUserIdFromAccessToken(token);
            log.debug("🔑 Extracted userId from token: {}", userId);

            // ⭐ DB에서 사용자 조회
            User user = userRepository.findById(userId).orElse(null);
            if (user == null || !user.isActive()) {
                log.warn("⚠️ User not found or inactive: {}", userId);
                chain.doFilter(req, res);
                return;
            }

            // ⭐ CustomUser 생성 및 인증 설정
            CustomUser customUser = new CustomUser(
                    user.getUsersId(),
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            customUser,
                            null,
                            customUser.getAuthorities()
                    );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("✅ Authentication set for userId: {}", userId);

        } catch (Exception e) {
            log.error("❌ JWT authentication failed: {}", e.getMessage(), e);
        }

        chain.doFilter(req, res);
    }

    private String resolveToken(HttpServletRequest req) {
        // Authorization 헤더에서 Bearer 토큰 추출
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}