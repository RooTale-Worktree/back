package com.Rootale.security;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuthUserInfoFactory {
    public OAuthDto.OAuthUserInfo from(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> new OAuthDto.GoogleUserInfo(attributes);
            case "kakao"  -> new OAuthDto.KakaoUserInfo(attributes);
            case "naver"  -> new OAuthDto.NaverUserInfo(attributes);
            default -> throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
        };
    }
}
