package com.LiveToon.domain.fcm.service;

import com.LiveToon.domain.fcm.NotificationParameter;
import com.LiveToon.domain.fcm.web.dto.PushNotificationRequest;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;


//Firebase Admin SDK를 사용하여 FCM 메시지를 직접 구성하고 전송하는 서비스입니다.
@Service
@RequiredArgsConstructor
public class FCMService {

    private Logger logger = LoggerFactory.getLogger(FCMService.class);
    private final FCMTokenManagementService fcmTokenManagementService;

    /**
     * 데이터 페이로드를 포함하는 메시지를 특정 토픽(Topic)으로 전송합니다.
     *
     * @param data    알림과 함께 보낼 Key-Value 형태의 데이터 페이로드
     * @param request 알림의 제목, 내용, 타겟 토픽 정보를 담은 DTO
     * @throws InterruptedException 비동기 작업 대기 중 스레드가 중단될 경우 발생
     * @throws ExecutionException   비동기 작업 실행 중 오류가 발생할 경우
     */
    public void sendMessage(Map<String, String> data, PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        // 데이터가 포함된 토픽 메시지를 생성하고, 실제 전송 로직을 호출합니다.
        Message message = getPreconfiguredMessageWithData(data, request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + response);
    }

    /**
     * 데이터 페이로드 없이 순수한 알림 메시지만을 특정 토픽(Topic)으로 전송합니다.
     *
     * @param request 알림의 제목, 내용, 타겟 토픽 정보를 담은 DTO
     * @throws InterruptedException 비동기 작업 대기 중 스레드가 중단될 경우 발생
     * @throws ExecutionException   비동기 작업 실행 중 오류가 발생할 경우
     */
    public void sendMessageWithoutData(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        // 데이터가 없는 토픽 메시지를 생성하고, 실제 전송 로직을 호출합니다.
        Message message = getPreconfiguredMessageWithoutData(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
    }

    /**
     * 특정 디바이스 토큰(Token)으로 1:1 알림 메시지를 전송합니다.
     *
     * @param request 알림의 제목, 내용, 타겟 토큰 정보를 담은 DTO
     * @throws InterruptedException 비동기 작업 대기 중 스레드가 중단될 경우 발생
     * @throws ExecutionException   비동기 작업 실행 중 오류가 발생할 경우
     */
    public void sendMessageToToken(PushNotificationRequest request) throws  InterruptedException, ExecutionException {
        try {
            Message message = getPreconfiguredMessageToToken(request);
            String response = sendAndGetResponse(message);
            fcmTokenManagementService.updateTokenCheckTime(request.getToken());
            logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response);

        } catch (InterruptedException e) {
            logger.error("FCM message sending was interrupted for token: {}", request.getToken(), e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FirebaseMessagingException) {
                fcmTokenManagementService.handleInvalidToken(request.getToken());
            }
        }
    }

    /**
     * 완전히 구성된 Message 객체를 받아 FCM 서버로 전송하고, 응답을 반환하는 핵심 실행 메소드입니다.
     *
     * @param message 전송할 최종 Message 객체
     * @return FCM 서버로부터 받은 응답 문자열 (보통 메시지 ID)
     * @throws InterruptedException 비동기 작업 대기 중 스레드가 중단될 경우 발생
     * @throws ExecutionException   비동기 작업 실행 중 오류가 발생할 경우
     */
    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        // 메시지를 비동기 방식으로 전송하고, 작업이 완료될 때까지 기다린 후 결과를 가져옵니다.
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    /**
     * 안드로이드 기기를 위한 푸시 알림의 상세 설정을 생성합니다.
     *
     * @param topic 알림을 그룹화하거나 대체하는 데 사용될 'Collapse Key' 및 'Tag'로 사용될 토픽 이름
     * @return 구성된 AndroidConfig 객체
     */
    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                // 메시지 수명(Time To Live) 설정. 2분 후 기기가 오프라인이면 메시지 소멸.
                .setTtl(Duration.ofMinutes(2).toMillis())
                // 동일한 Collapse Key를 가진 메시지가 연속 도착 시, 마지막 메시지만 표시되도록 함.
                .setCollapseKey(topic)
                // 메시지 중요도. HIGH는 즉시 전송을 의미.
                .setPriority(AndroidConfig.Priority.HIGH)
                // 안드로이드 알림의 시각/청각적 요소 설정
                .setNotification(AndroidNotification.builder()
                        .setSound(NotificationParameter.SOUND.getValue()) // 알림 소리
                        .setColor(NotificationParameter.COLOR.getValue()) // 알림 아이콘 색상
                        .setTag(topic) // Collapse Key와 함께 알림을 식별하는 태그
                        .build())
                .build();
    }

    /**
     * iOS 기기를 위한 APNS(Apple Push Notification Service) 상세 설정을 생성합니다.
     *
     * @param topic iOS에서 알림을 그룹화하는 데 사용될 'category' 및 'thread-id'로 사용될 토픽 이름
     * @return 구성된 ApnsConfig 객체
     */
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(topic) // 알림 카테고리 설정
                        .setThreadId(topic) // 동일한 ID를 가진 알림을 그룹화
                        .build())
                .build();
    }

    /**
     * 특정 토큰을 타겟으로 하는 Message 객체를 조립합니다.
     *
     * @param request 제목, 내용, 타겟 토큰이 포함된 요청 DTO
     * @return 토큰 타겟팅이 완료된 Message 객체
     */
    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
        // 공통 빌더를 가져온 뒤, 타겟으로 토큰을 설정하여 최종 Message 객체를 생성합니다.
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }

    /**
     * 데이터가 없는 단순 알림 Message 객체를 조립합니다. (타겟: 토픽)
     *
     * @param request 제목, 내용, 타겟 토픽이 포함된 요청 DTO
     * @return 토픽 타겟팅이 완료된 Message 객체
     */
    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
        // 공통 빌더를 가져온 뒤, 타겟으로 토픽을 설정하여 최종 Message 객체를 생성합니다.
        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
                .build();
    }

    /**
     * 데이터 페이로드를 포함하는 Message 객체를 조립합니다. (타겟: 토픽)
     *
     * @param data    메시지에 포함할 데이터 맵
     * @param request 제목, 내용, 타겟 토픽이 포함된 요청 DTO
     * @return 데이터와 토픽 타겟팅이 완료된 Message 객체
     */
    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
        // 공통 빌더를 가져온 뒤, 데이터 페이로드를 추가하고 타겟으로 토픽을 설정하여 최종 Message 객체를 생성합니다.
        return getPreconfiguredMessageBuilder(request).putAllData(data).setTopic(request.getTopic())
                .build();
    }

    /**
     * 모든 Message 객체 생성에 필요한 공통 설정을 담은 Message.Builder를 생성하여 반환합니다.
     * 이 메소드는 코드 중복을 방지하는 역할을 합니다.
     *
     * @param request 공통적으로 필요한 제목, 내용이 포함된 요청 DTO
     * @return 안드로이드/iOS 공통 설정 및 알림 내용이 설정된 Message.Builder 객체
     */
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        // 모든 메시지에 공통적으로 적용될 설정을 빌더에 추가합니다.
        return Message.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(new Notification(request.getTitle(), request.getMessage()));
    }
}
