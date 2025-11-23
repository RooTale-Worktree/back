package com.Rootale.security;

import com.Rootale.member.entity.OAuthAccount;
import com.Rootale.member.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Map;

/**
 * OAuth 및 User 관련 모든 DTO를 포함하는 컨테이너 클래스
 */
public class OAuthDto {

    // ==================== Request DTOs ====================
    /**
     * 소셜 로그인 요청
     */
    public record SocialLoginRequest(
            @NotBlank String provider,
            @NotBlank @Email String email,
            @NotBlank String token,
            String fcm_token
    ) {}

    /**
     * 사용자 정보 수정 요청
     */
    public record UpdateUserRequest(
            @Size(max = 50, message = "닉네임은 50자를 넘을 수 없습니다")
            String nickname,

            @Size(max = 500, message = "아바타 URL은 500자를 넘을 수 없습니다")
            String avatar_url
    ) {}

    /**
     * Refresh Token 요청
     */
    public record RefreshTokenRequest(
            @NotBlank(message = "refresh_token은 필수입니다")
            String refresh_token
    ) {}

    // ==================== Response DTOs ====================

    /**
     * 로그인 응답
     */
    public record LoginResponse(
            String token,
            String refresh_token,
            long expires_in
    ) {
        public static OAuthDto.LoginResponse of(String accessToken, String refreshToken, long expiresInSeconds) {
            return new OAuthDto.LoginResponse(accessToken, refreshToken, expiresInSeconds);
        }
    }

    /**
     * 로그아웃 응답
     */
    public record LogoutResponse(
            String message,
            boolean fcm_token_removed
    ) {
        public static LogoutResponse of(String message, boolean fcmTokenRemoved) {
            return new LogoutResponse(message, fcmTokenRemoved);
        }
    }

    /**
     * 사용자 정보 응답
     */
    public record UserResponse(
            String id,
            String email,
            String username,
            String nickname,
            @JsonProperty("avatar_url") String avatarUrl,
            String provider,
            @JsonProperty("provider_id") String providerId,
            @JsonProperty("created_at") Instant createdAt,
            @JsonProperty("updated_at") Instant updatedAt,
            @JsonProperty("is_active") boolean isActive,
            @JsonProperty("subscription_tier") String subscriptionTier
    ) {
        public static UserResponse from(User user) {
            OAuthAccount primaryOAuth = user.getOauthAccounts().isEmpty()
                    ? null
                    : user.getOauthAccounts().get(0);

            return new UserResponse(
                    user.getUsersId() != null ? user.getUsersId().toString() : null,
                    user.getEmail(),
                    user.getName(),
                    user.getNickname(),
                    user.getAvatarURL(),
                    primaryOAuth != null ? primaryOAuth.getProvider() : null,
                    primaryOAuth != null ? primaryOAuth.getProviderUserId() : null,
                    primaryOAuth != null ? primaryOAuth.getLinkedAt() : Instant.now(),
                    Instant.now(),
                    user.isActive(),
                    user.getSubscriptionTier()
            );
        }
    }

    /**
     * 회원 탈퇴 응답
     */
    public record WithdrawResponse(
            String message,
            Instant deleted_at
    ) {
        public static WithdrawResponse of(String message) {
            return new WithdrawResponse(message, Instant.now());
        }
    }

    // ==================== OAuth UserInfo Interfaces & Implementations ====================

    /**
     * OAuth Provider별 사용자 정보 인터페이스
     */
    public interface OAuthUserInfo {
        String provider();
        String providerUserId();
        String email();
        String username();
        String picture();
        Map<String, Object> attributes();
    }

    /**
     * Google 사용자 정보
     */
    public static class GoogleUserInfo implements OAuthUserInfo {
        private final Map<String, Object> attributes;

        public GoogleUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String provider() {
            return "google";
        }

        @Override
        public String providerUserId() {
            return (String) attributes.get("sub");
        }

        @Override
        public String email() {
            return (String) attributes.get("email");
        }

        @Override
        public String username() {
            String emailVal = email();
            return emailVal != null ? emailVal : providerUserId() + "@google.local";
        }

        @Override
        public String picture() {
            return (String) attributes.get("picture");
        }

        @Override
        public Map<String, Object> attributes() {
            return attributes;
        }
    }

    /**
     * Kakao 사용자 정보
     */
    public static class KakaoUserInfo implements OAuthUserInfo {
        private final Map<String, Object> attributes;
        private final Map<String, Object> kakaoAccount;
        private final Map<String, Object> profile;

        @SuppressWarnings("unchecked")
        public KakaoUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
            this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            this.profile = kakaoAccount != null ? (Map<String, Object>) kakaoAccount.get("profile") : null;
        }

        @Override
        public String provider() {
            return "kakao";
        }

        @Override
        public String providerUserId() {
            Object id = attributes.get("id");
            return id != null ? String.valueOf(id) : null;
        }

        @Override
        public String email() {
            if (kakaoAccount == null) return null;
            return (String) kakaoAccount.get("email");
        }

        @Override
        public String username() {
            String emailVal = email();
            return emailVal != null ? emailVal : providerUserId() + "@kakao.local";
        }

        @Override
        public String picture() {
            if (profile == null) return null;
            return (String) profile.get("profile_image_url");
        }

        @Override
        public Map<String, Object> attributes() {
            return attributes;
        }
    }

    /**
     * Naver 사용자 정보
     */
    public static class NaverUserInfo implements OAuthUserInfo {
        private final Map<String, Object> attributes;
        private final Map<String, Object> response;

        @SuppressWarnings("unchecked")
        public NaverUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
            this.response = (Map<String, Object>) attributes.get("response");
        }

        @Override
        public String provider() {
            return "naver";
        }

        @Override
        public String providerUserId() {
            if (response == null) return null;
            return (String) response.get("id");
        }

        @Override
        public String email() {
            if (response == null) return null;
            return (String) response.get("email");
        }

        @Override
        public String username() {
            String emailVal = email();
            return emailVal != null ? emailVal : providerUserId() + "@naver.local";
        }

        @Override
        public String picture() {
            if (response == null) return null;
            return (String) response.get("profile_image");
        }

        @Override
        public Map<String, Object> attributes() {
            return attributes;
        }
    }
}
