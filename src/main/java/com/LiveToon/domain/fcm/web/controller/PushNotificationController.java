package com.LiveToon.domain.fcm.web.controller;

import com.LiveToon.domain.fcm.service.PushNotificationService;
import com.LiveToon.domain.fcm.web.dto.PushNotificationRequest;
import com.LiveToon.domain.fcm.web.dto.PushNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// FCM 푸시알람을 위한 컨트롤러
@RestController
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    /**
     * 특정 토픽(Topic)으로 데이터 페이로드 없는 단순 알림을 전송합니다.
     * POST /notification/topic
     *
     * @param request 알림의 제목, 내용, 토픽 정보를 담은 요청 DTO
     * @return 알림 전송 결과 응답
     */
    @PostMapping("/notification/topic")
    public ResponseEntity<PushNotificationResponse> sendNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationWithoutData(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    /**
     * 특정 디바이스 토큰(Token)으로 1:1 알림을 전송합니다.
     * POST /notification/token
     *
     * @param request 알림의 제목, 내용, 디바이스 토큰 정보를 담은 요청 DTO
     * @return 알림 전송 결과 응답
     */
    @PostMapping("/notification/token")
    public ResponseEntity<PushNotificationResponse> sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    /**
     * 데이터 페이로드를 포함한 알림을 전송합니다.
     * POST /notification/data
     *
     * @param request 알림의 제목, 내용, 토픽 정보를 담은 요청 DTO
     * @return 알림 전송 결과 응답
     */
    @PostMapping("/notification/data")
    public ResponseEntity<PushNotificationResponse> sendDataNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotification(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    /**
     * 서버에 미리 설정된 기본값으로 샘플 알림을 전송합니다. (테스트용)
     * GET /notification
     *
     * @return 알림 전송 결과 응답
     */
    @GetMapping("/notification")
    public ResponseEntity<PushNotificationResponse> sendSampleNotification() {
        pushNotificationService.sendSamplePushNotification();
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
}

