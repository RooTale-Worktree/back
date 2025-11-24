package com.Rootale.fcm.web.controller;

import com.Rootale.fcm.service.PushNotificationService;
import com.Rootale.fcm.web.dto.PushNotificationRequest;
import com.Rootale.fcm.web.dto.PushNotificationResponse;
import com.Rootale.fcm.web.dto.SubscribeRequest;
import com.Rootale.fcm.web.dto.SubscribeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// admin의 FCM 푸시알람을 위한 컨트롤러
@RestController
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    /**
     * POST /notification
     * 개발자 테스트 or 관리자용 메소드입니다.
     * @param request 알림의 제목, 내용, 토픽 정보를 담은 요청 DTO
     * @return 알림 전송 결과 응답
     */
    @PostMapping("/notification")
    public ResponseEntity<PushNotificationResponse> sendNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);


    }

    /**
     * POST /notification/topic
     * 관리자용 메소드입니다.
     * @param request 알림의 제목, 내용, 토픽 정보를 담은 요청 DTO
     * @return 알림 전송 결과 응답
     */
    @PostMapping("/notification/topic")
    public ResponseEntity<PushNotificationResponse> sendNotificationToTopic(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToTopic(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);


    }

    @PostMapping("/subscribe")
    public ResponseEntity<SubscribeResponse> subscribeTopic(@RequestBody SubscribeRequest request) {
        pushNotificationService.subscribeTopic(request);
        return new ResponseEntity<>(new SubscribeResponse(HttpStatus.OK.value(), "Subscribing topic is completed"), HttpStatus.OK);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<SubscribeResponse> unsubscribeTopic(@RequestBody SubscribeRequest request) {
        pushNotificationService.unsubscribeTopic(request);
        return new ResponseEntity<>(new SubscribeResponse(HttpStatus.OK.value(), "Subscribing topic is completed"), HttpStatus.OK);
    }
}

