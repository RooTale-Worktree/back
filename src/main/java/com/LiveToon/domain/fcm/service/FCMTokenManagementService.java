package com.LiveToon.domain.fcm.service;

import com.LiveToon.domain.fcm.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FCMTokenManagementService {

    private final FCMTokenRepository fcmTokenRepository;
    private final Logger logger = LoggerFactory.getLogger(FCMTokenManagementService.class);

    /**
     * 로그아웃 시 사용자의 특정 디바이스 토큰을 삭제합니다.
     * 로그아웃 API에서 호출됩니다.
     */
    @Transactional
    public void deleteTokenOnLogout(String deviceToken) {
        if (deviceToken == null || deviceToken.isBlank()) {
            return;
        }
        fcmTokenRepository.deleteByDeviceToken(deviceToken);
        logger.info("로그아웃 처리: User {}의 Token {} 삭제 완료", deviceToken);
    }

    /**
     * 토큰이 여전히 활성 상태임을 확인하고 마지막 체크 시간을 갱신합니다.
     * 프론트엔드의 '생존 신고' API에서 호출될 예정입니다.
     */
    @Transactional
    public void updateTokenCheckTime(String deviceToken) {
        if (deviceToken == null || deviceToken.isBlank()) {
            return;
        }
        fcmTokenRepository.findByDeviceToken(deviceToken)
                .ifPresent(fcmToken -> {
                    fcmToken.updateTokenCheckTime(LocalDateTime.now()); // Entity 내부에 시간 갱신 로직
                    logger.info("토큰 활성 시간 갱신: Token {}", deviceToken);
                });
    }

    /**
     * FCM 발송 실패 시, 유효하지 않은 토큰을 DB에서 삭제합니다.
     * FCMService의 에러 처리 로직에서 호출됩니다.
     */
    @Transactional
    public void handleInvalidToken(String deviceToken) {
        if (deviceToken == null || deviceToken.isBlank()) {
            return;
        }
        fcmTokenRepository.deleteByDeviceToken(deviceToken);
        logger.warn("유효하지 않은 토큰 삭제 처리: Token {}", deviceToken);
    }
}

