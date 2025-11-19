package com.Rootale.security.service;

import com.Rootale.member.entity.User;
import com.Rootale.security.OAuthDto;
import com.Rootale.security.jwt.JwtTokenService;
import com.Rootale.security.OAuthUserInfoFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j // ⭐ 로깅 추가
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuthUserInfoFactory userInfoFactory;
    private final OAuthAccountService linkService;
    private final JwtTokenService jwtTokenService;

    @Value("${oauth2.success-redirect:/}")
    private String successRedirect;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
                                        Authentication authentication) throws IOException {
        log.info("🔵 OAuth2 success handler triggered");

        if (!(authentication instanceof OAuth2AuthenticationToken token)) {
            log.warn("⚠️ Authentication is not OAuth2AuthenticationToken");
            res.sendRedirect(successRedirect);
            return;
        }

        String registrationId = token.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        log.info("🔵 Provider: {}, Attributes: {}", registrationId, attributes);

        // 1) provider-중립 유저 정보
        OAuthDto.OAuthUserInfo info = userInfoFactory.from(registrationId, attributes);

        // 2) DB 연동 (없으면 생성, 있으면 연결 반환)
        User user = linkService.linkOrCreateUser(info);
        log.info("✅ User from success handler - userId: {}, username: {}", 
                 user.getUsersId(), user.getUsername());

        // 3) JWT 발급(access, refresh) + 쿠키
        String access = jwtTokenService.createAccessToken(user);
        String refresh = jwtTokenService.createRefreshToken(user);
        log.info("✅ JWT tokens created");
        
        // Access Token 쿠키
        Cookie accessCookie = new Cookie("ACCESS_TOKEN", access);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(30 * 60);
        res.addCookie(accessCookie);
        
        // Refresh Token 쿠키
        Cookie refreshCookie = new Cookie("REFRESH_TOKEN", refresh);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(14 * 24 * 60 * 60);
        res.addCookie(refreshCookie);

        log.info("✅ Redirecting to: {}", successRedirect);
        res.sendRedirect(successRedirect);
    }
}
