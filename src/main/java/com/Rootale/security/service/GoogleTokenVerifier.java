package com.Rootale.security.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GoogleTokenVerifier {

    @Value("${google.client-id.ios}")
    private String iosClientId;

    @Value("${google.client-id.android}")
    private String androidClientId;

    @Value("${google.client-id.web:}")
    private String webClientId;

    /**
     * Google ID Token을 검증하고 사용자 정보를 반환합니다.
     */
    public Map<String, Object> verifyIdToken(String idTokenString) {
        try {
            // ⭐ Google의 공식 라이브러리로 ID Token 검증
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
                    // iOS, Android, Web Client ID 모두 허용
                    .setAudience(Arrays.asList(iosClientId, androidClientId, webClientId))
                    .build();

            log.debug("🔍 Verifying Google ID Token...");
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                log.error("❌ Invalid Google ID Token");
                throw new RuntimeException("유효하지 않은 Google ID Token입니다.");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // ⭐ 토큰 정보 추출
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("sub", payload.getSubject());           // User ID
            userInfo.put("email", payload.getEmail());           // Email
            userInfo.put("email_verified", payload.getEmailVerified());
            userInfo.put("name", payload.get("name"));          // Full name
            userInfo.put("picture", payload.get("picture"));    // Profile picture
            userInfo.put("given_name", payload.get("given_name"));
            userInfo.put("family_name", payload.get("family_name"));
            userInfo.put("locale", payload.get("locale"));
            userInfo.put("aud", payload.getAudience());         // Client ID (audience)

            log.info("✅ Google ID Token verified - sub: {}, email: {}, aud: {}",
                    payload.getSubject(), payload.getEmail(), payload.getAudience());

            return userInfo;

        } catch (Exception e) {
            log.error("❌ Failed to verify Google ID Token: {}", e.getMessage(), e);
            throw new RuntimeException("Google ID Token 검증 실패: " + e.getMessage());
        }
    }

    /**
     * Audience(Client ID) 검증
     */
    public void verifyAudience(String audience) {
        if (audience == null) {
            throw new IllegalArgumentException("Audience가 없습니다.");
        }

        // iOS, Android, Web 중 하나와 일치하는지 확인
        if (!iosClientId.equals(audience) &&
                !androidClientId.equals(audience) &&
                (webClientId == null || !webClientId.equals(audience))) {

            log.error("❌ Invalid audience: expected [iOS: {}, Android: {}, Web: {}], got: {}",
                    iosClientId, androidClientId, webClientId, audience);
            throw new IllegalArgumentException(
                    String.format("유효하지 않은 Google 클라이언트 ID입니다. 받은 값: %s", audience)
            );
        }

        log.info("✅ Audience verified: {}", audience);
    }
}