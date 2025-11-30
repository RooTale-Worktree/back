package com.Rootale.security.service;

import com.Rootale.fcm.entity.FcmToken;
import com.Rootale.member.entity.*;
import com.Rootale.member.enums.UserType;
import com.Rootale.member.repository.*;
import com.Rootale.security.OAuthDto.*;
import com.Rootale.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OAuthAccountRepository oauthAccountRepository;
    private final JwtTokenService jwtTokenService;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final AppleTokenVerifier appleTokenVerifier;
    private final KakaoTokenVerifier kakaoTokenVerifier;

    @Value("${jwt.access-expiration:1800000}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration:1209600000}")
    private long refreshExpiration;

    @Transactional
    public LoginResponse socialLogin(SocialLoginRequest request) {
        String provider = request.provider().toLowerCase();
        String idToken = request.token();
        String email = request.email();
        String fcmToken = request.fcm_token();

        log.info("🔵 Social login - provider: {}, email: {}", provider, email);

        // ⭐ Google은 ID Token 검증, Kakao/Naver는 Access Token으로 UserInfo 조회
        Map<String, Object> userInfo = fetchUserInfoFromProvider(provider, idToken);
        String providerUserId = extractProviderUserId(provider, userInfo);
        String verifiedEmail = extractEmail(provider, userInfo);
        String pictureUrl = extractPictureUrl(provider, userInfo);

        if (!email.equalsIgnoreCase(verifiedEmail)) {
            log.warn("⚠️ Email mismatch - requested: {}, verified: {}", email, verifiedEmail);
            throw new IllegalArgumentException("이메일 정보가 일치하지 않습니다.");
        }

        log.info("✅ Token verified - provider: {}, providerId: {}, email: {}", provider, providerUserId, verifiedEmail);

        OAuthAccount oauthAccount = oauthAccountRepository
                .findByProviderAndProviderUserId(provider, providerUserId)
                .orElseGet(() -> {
                    User existingUser = userRepository.findByEmail(email).orElse(null);
                    if (existingUser != null) {
                        log.info("✅ Found existing user by email - userId: {}", existingUser.getUsersId());
                        return linkOAuthToExistingUser(existingUser, provider, providerUserId, email, pictureUrl);
                    }
                    return createNewUserWithOAuth(provider, providerUserId, email, pictureUrl);
                });

        User user = oauthAccount.getUser();

        if (fcmToken != null && !fcmToken.isBlank()) {
            updateFcmToken(user, fcmToken);
        }

        String jwtAccessToken = jwtTokenService.createAccessToken(user);
        String jwtRefreshToken = jwtTokenService.createRefreshToken(user);

        log.info("✅ JWT tokens issued for userId: {}", user.getUsersId());

        return LoginResponse.of(jwtAccessToken, jwtRefreshToken, accessExpiration / 1000);
    }

    /**
     * ⭐ Google ID Token 또는 Kakao/Naver Access Token으로 사용자 정보 조회
     */
    private Map<String, Object> fetchUserInfoFromProvider(String provider, String token) {
        try {
            if ("google".equals(provider)) {
                // ⭐ Google: 공식 라이브러리로 ID Token 검증
                log.debug("🔍 Verifying Google ID Token with official library");
                return googleTokenVerifier.verifyIdToken(token);
            }

            if ("apple".equals(provider)) {
                log.debug("🔍 Verifying Apple ID Token");
                return appleTokenVerifier.verifyIdToken(token);
            }

            if ("kakao".equals(provider)) {
                // ⭐ Kakao: KakaoTokenVerifier로 Access Token 검증
                log.debug("🔍 Verifying Kakao Access Token");
                return kakaoTokenVerifier.verifyAccessToken(token);
            }

            // Naver: Access Token으로 UserInfo API 호출
            if ("naver".equals(provider)) {
                String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
                log.debug("🔍 Fetching user info from {} with access token", provider);
                RestClient restClient = RestClient.create();
                Map<String, Object> response = restClient.get()
                        .uri(userInfoUrl)
                        .header("Authorization", "Bearer " + token.trim())
                        .retrieve()
                        .body(Map.class);

                if (response == null) {
                    throw new RuntimeException("소셜 플랫폼으로부터 사용자 정보를 받을 수 없습니다.");
                }

                log.debug("✅ User info received from {}: {}", provider, response.keySet());
                return response;
            }

            throw new IllegalArgumentException("Unsupported provider: " + provider);


        } catch (RestClientException e) {
            log.error("❌ Failed to fetch user info from {}: {}", provider, e.getMessage());
            throw new RuntimeException("유효하지 않은 access token입니다: " + e.getMessage());
        }
    }


    @Transactional
    public LogoutResponse logout(Long userId) {
        log.info("🔴 Logout - userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        boolean fcmTokenRemoved = false;
        if (!user.getFcmTokens().isEmpty()) {
            user.getFcmTokens().forEach(token -> token.setIsActive(false));
            fcmTokenRemoved = true;
            log.info("📱 FCM tokens deactivated for userId: {}", userId);
        }

        return LogoutResponse.of("로그아웃되었습니다", fcmTokenRemoved);
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(Long userId) {
        log.info("👤 Get current user - userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        log.info("✏️ Update user - userId: {}, nickname: {}", userId, request.nickname());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (request.nickname() != null && !request.nickname().isBlank()) {
            user.setNickname(request.nickname());
        }
        if (request.avatar_url() != null && !request.avatar_url().isBlank()) {
            user.setAvatarURL(request.avatar_url());
        }

        User savedUser = userRepository.save(user);
        log.info("✅ User updated - userId: {}", userId);
        return UserResponse.from(savedUser);
    }

    @Transactional
    public LoginResponse refreshAccessToken(String refreshToken) {
        log.info("🔄 Refresh access token");
        try {
            Long userId = jwtTokenService.extractUserIdFromRefreshToken(refreshToken);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            if (!user.isActive()) {
                throw new IllegalArgumentException("비활성화된 사용자입니다.");
            }

            String newAccessToken = jwtTokenService.createAccessToken(user);
            String newRefreshToken = jwtTokenService.createRefreshToken(user);

            log.info("✅ Tokens refreshed for userId: {}", userId);
            return LoginResponse.of(newAccessToken, newRefreshToken, accessExpiration / 1000);
        } catch (Exception e) {
            log.error("❌ Token refresh failed: {}", e.getMessage());
            throw new RuntimeException("유효하지 않은 refresh token입니다.");
        }
    }

    @Transactional
    public WithdrawResponse withdraw(Long userId) {
        log.info("🗑️ User withdrawal - userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setActive(false);
        user.getFcmTokens().forEach(token -> token.setIsActive(false));
        userRepository.save(user);

        log.info("✅ User withdrawn - userId: {}", userId);
        return WithdrawResponse.of("회원 탈퇴가 완료되었습니다");
    }


    private String extractProviderUserId(String provider, Map<String, Object> userInfo) {
        return switch (provider) {
            case "google" -> (String) userInfo.get("sub");
            case "kakao" -> String.valueOf(userInfo.get("id"));
            case "naver" -> {
                @SuppressWarnings("unchecked")
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                yield response != null ? (String) response.get("id") : null;
            }
            default -> null;
        };
    }

    private String extractEmail(String provider, Map<String, Object> userInfo) {
        return switch (provider) {
            case "google" -> (String) userInfo.get("email");
            case "kakao" -> {
                @SuppressWarnings("unchecked")
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
                yield kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
            }
            case "naver" -> {
                @SuppressWarnings("unchecked")
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                yield response != null ? (String) response.get("email") : null;
            }
            default -> null;
        };
    }

    private String extractPictureUrl(String provider, Map<String, Object> userInfo) {
        return switch (provider) {
            case "google" -> (String) userInfo.get("picture");
            case "apple" -> null;  // Apple은 프로필 이미지 제공 안함
            case "kakao" -> {
                @SuppressWarnings("unchecked")
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
                if (kakaoAccount != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                    yield profile != null ? (String) profile.get("profile_image_url") : null;
                }
                yield null;
            }
            case "naver" -> {
                @SuppressWarnings("unchecked")
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                yield response != null ? (String) response.get("profile_image") : null;
            }
            default -> null;
        };
    }

    private OAuthAccount linkOAuthToExistingUser(User user, String provider, String providerUserId,
                                                 String email, String pictureUrl) {
        log.info("🔗 Linking OAuth to existing user - userId: {}, provider: {}", user.getUsersId(), provider);
        OAuthAccount oauthAccount = OAuthAccount.builder()
                .user(user)
                .provider(provider)
                .providerUserId(providerUserId)
                .email(email)
                .pictureUrl(pictureUrl)
                .build();
        return oauthAccountRepository.save(oauthAccount);
    }

    private OAuthAccount createNewUserWithOAuth(String provider, String providerUserId,
                                                String email, String pictureUrl) {
        log.info("🆕 Creating new user - provider: {}, email: {}", provider, email);
        User user = User.builder()
                .email(email)
                .name(email.split("@")[0])
                .nickname(email.split("@")[0])
                .avatarURL(pictureUrl)
                .subscriptionTier("FREE")
                .userType(UserType.SOCIAL)
                .isActive(true)
                .build();
        User savedUser = userRepository.save(user);

        OAuthAccount oauthAccount = OAuthAccount.builder()
                .user(savedUser)
                .provider(provider)
                .providerUserId(providerUserId)
                .email(email)
                .pictureUrl(pictureUrl)
                .build();
        return oauthAccountRepository.save(oauthAccount);
    }

    // User 도메인에서 fcm토큰 처리를 하는 게 좋을지? 둘의 연관성은 매우 강하긴 함.
    private void updateFcmToken(User user, String fcmToken) {
        log.info("📱 Updating FCM token for userId: {}", user.getUsersId());
        FcmToken token = user.getFcmTokens().stream()
                .filter(t -> t.getDeviceToken().equals(fcmToken))
                .findFirst()
                .orElseGet(() -> {
                    FcmToken newToken = FcmToken.builder()
                            .user(user)
                            .deviceToken(fcmToken)
                            //.isActive(true)
                            //.notificationEnabled(true)
                            .build();
                    user.getFcmTokens().add(newToken);
                    return newToken;
                });
        token.setTokenCheckTime(LocalDateTime.now());
        token.setIsActive(true);
    }

}