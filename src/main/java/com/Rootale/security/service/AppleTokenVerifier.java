package com.Rootale.security.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleTokenVerifier {

    private final ObjectMapper objectMapper;

    @Value("${spring.security.oauth2.client.registration.apple.client-id:com.livetoon.rootale}")
    private String clientId;

    private static final String APPLE_PUBLIC_KEYS_URL = "https://appleid.apple.com/auth/keys";
    private static final String APPLE_ISSUER = "https://appleid.apple.com";

    /**
     * Apple ID Token 검증 및 사용자 정보 반환
     */
    public Map<String, Object> verifyIdToken(String idToken) {
        try {
            log.debug("🔍 Verifying Apple ID Token");

            // 1. ID Token 헤더에서 kid 추출
            String[] tokenParts = idToken.split("\\.");
            if (tokenParts.length != 3) {
                throw new IllegalArgumentException("Invalid ID token format");
            }

            String headerJson = new String(Base64.getUrlDecoder().decode(tokenParts[0]));
            JsonNode headerNode = objectMapper.readTree(headerJson);
            String kid = headerNode.path("kid").asText();

            log.debug("🔑 Token kid: {}", kid);

            // 2. Apple 공개키 가져오기
            PublicKey publicKey = getApplePublicKey(kid);

            // 3. JWT 검증
            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .requireIssuer(APPLE_ISSUER)
                    .requireAudience(clientId)
                    .build()
                    .parseClaimsJws(idToken)
                    .getBody();

            log.debug("✅ Apple ID Token verified - sub: {}", claims.getSubject());

            // 4. Claims를 Map으로 변환
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("sub", claims.getSubject());
            userInfo.put("email", claims.get("email", String.class));
            userInfo.put("email_verified", claims.get("email_verified", Boolean.class));
            userInfo.put("is_private_email", claims.get("is_private_email", Boolean.class));

            return userInfo;

        } catch (Exception e) {
            log.error("❌ Apple ID Token verification failed: {}", e.getMessage(), e);
            throw new RuntimeException("Invalid Apple ID token: " + e.getMessage(), e);
        }
    }

    /**
     * Apple 공개키 가져오기
     */
    private PublicKey getApplePublicKey(String kid) throws Exception {
        RestClient restClient = RestClient.create();

        String response = restClient.get()
                .uri(APPLE_PUBLIC_KEYS_URL)
                .retrieve()
                .body(String.class);

        JsonNode keysNode = objectMapper.readTree(response);
        JsonNode keys = keysNode.path("keys");

        // kid와 일치하는 공개키 찾기
        for (JsonNode keyNode : keys) {
            if (kid.equals(keyNode.path("kid").asText())) {
                return buildPublicKey(keyNode);
            }
        }

        throw new IllegalArgumentException("No matching public key found for kid: " + kid);
    }

    /**
     * JWK에서 RSA PublicKey 생성
     */
    private PublicKey buildPublicKey(JsonNode keyNode) throws Exception {
        String nStr = keyNode.path("n").asText();
        String eStr = keyNode.path("e").asText();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr);
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr);

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(publicKeySpec);
    }
}