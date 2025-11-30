package com.Rootale.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoTokenVerifier {

    @Value("${kakao.app-key}")
    private String appKey;

    /**
     * Kakao Access Token을 검증하고 사용자 정보를 반환합니다.
     */
    public Map<String, Object> verifyAccessToken(String accessToken) {
        try {
            log.debug("🔍 Verifying Kakao access token");

            RestClient restClient = RestClient.create();
            Map<String, Object> response = restClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", "Bearer " + accessToken.trim())
                    .retrieve()
                    .body(Map.class);

            if (response == null) {
                throw new RuntimeException("카카오로부터 사용자 정보를 받을 수 없습니다.");
            }

            Object userId = response.get("id");
            log.debug("✅ Kakao token verified - userId: {}", userId);

            return response;

        } catch (RestClientException e) {
            log.error("❌ Failed to verify Kakao token: {}", e.getMessage());
            throw new RuntimeException("유효하지 않은 카카오 access token입니다: " + e.getMessage());
        }
    }
}
