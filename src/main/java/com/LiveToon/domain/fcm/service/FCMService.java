package com.LiveToon.domain.fcm.service;

import com.LiveToon.domain.fcm.NotificationParameter;
import com.LiveToon.domain.fcm.web.dto.PushNotificationDataSource;
import com.LiveToon.domain.fcm.web.dto.PushNotificationEvent;
import com.LiveToon.domain.fcm.web.dto.PushNotificationRequest;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
     * @param request 알림의 제목, 내용, 타겟 토픽 정보를 담은 DTO
     * @throws InterruptedException 비동기 작업 대기 중 스레드가 중단될 경우 발생
     * @throws ExecutionException   비동기 작업 실행 중 오류가 발생할 경우
     */
    public void sendMessageToTopic(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        // 데이터가 포함된 토픽 메시지를 생성하고, 실제 전송 로직을 호출합니다.
        Message message = getPreconfiguredMessageBuilder(request)
                .setTopic(request.getTopic())
                .build();
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + response);
    }

    /**
     * 특정 디바이스 토큰(Token)으로 1:1 알림 메시지를 전송합니다.
     *
     * @param request 알림의 제목, 내용, 타겟 토픽 정보를 담은 DTO
     * @throws InterruptedException 비동기 작업 대기 중 스레드가 중단될 경우 발생
     * @throws ExecutionException   비동기 작업 실행 중 오류가 발생할 경우
     */
    public void sendMessageToToken(PushNotificationRequest request)
            throws  InterruptedException, ExecutionException {
        try {
            Message message = getPreconfiguredMessageBuilder(request)
                    .setToken(request.getToken())
                    .build();
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
        } catch (Exception e) {
            logger.error("An unexpected error occurred while sending to token {}: {}", request.getToken(), e.getMessage(), e);
        }
    }

    /**
     * 여러 디바이스 토큰(Token)으로 알림 메시지를 전송합니다.
     * @param tokens 전송할 토큰 리스트
     * @param dataSource 알림 내용
     *
     * 한 유저의 여러 기기에 알람을 전송한다고 상정하고 만들었으나 문제점이 있습니다.
     * 각 기기가 다른 config를 가질 경우 그러한 부분을 반영하지 못하고 통일 config를 사용합니다.
     * 이를 위해 sendEach 메서드를 사용하며 FCM token 테이블도 수정해야 할 거 같아 추후 수정 예정입니다.
     */
    public void sendMessageToMultipleToken(List<String> tokens, PushNotificationDataSource dataSource)
            throws InterruptedException, ExecutionException {
        // 반환할 '삭제 대상' 토큰 리스트
        List<String> tokensToDelete = new ArrayList<>();

        MulticastMessage message = getPreconfiguredMulticastMessageBuilder(dataSource)
                .addAllTokens(tokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticastAsync(message).get();
            logger.info("Sent message to tokens");
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        String failedToken = tokens.get(i);
                        FirebaseMessagingException fcmException = responses.get(i).getException();

                        if (fcmException.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                            tokensToDelete.add(failedToken);
                        }
                    }
                }

            }

            if (!tokensToDelete.isEmpty()) {
                fcmTokenManagementService.handleInvalidTokens(tokensToDelete);
                logger.info("Successfully handled invalid tokens for deletion.");
            }
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof FirebaseMessagingException) {
                logger.error("Failed to send FCM message entirely", e);
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

    public void subscribeTopic(List<String> tokens, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                    tokens, topic);
            logger.info(response.getSuccessCount() + " tokens were subscribed successfully");
        } catch (Exception e) {
            logger.error("Error while subscribing to topic {}", topic, e);
        }
    }

    public void unsubscribeTopic(List<String> tokens, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance().unsubscribeFromTopic(
                    tokens, topic);
            logger.info(response.getSuccessCount() + " tokens were unsubscribed successfully");
        } catch (Exception e) {
            logger.error("Error while unsubscribing to topic {}", topic, e);
        }
    }

    /**
     * 안드로이드 기기를 위한 푸시 알림의 상세 설정을 생성합니다.
     * @return 구성된 AndroidConfig 객체
     */
    private AndroidConfig getAndroidConfig(PushNotificationDataSource dataSource) {
        String topic = dataSource.getTopic();

        // android가 null이면, '빈 맵'을 대신 사용합니다.
        Map<String, String> config = (dataSource.getAndroid() != null)
                ? dataSource.getAndroid()
                : Collections.emptyMap();

        String sound = config.getOrDefault("sound", NotificationParameter.DEFAULT_SOUND.getValue());
        String color = config.getOrDefault("color", NotificationParameter.DEFAULT_COLOR.getValue());
        String icon = config.getOrDefault("icon", NotificationParameter.DEFAULT_ANDROID_ICON.getValue());

        return AndroidConfig.builder()
                // 메시지 수명(Time To Live) 설정. 2분 후 기기가 오프라인이면 메시지 소멸.
                .setTtl(Duration.ofMinutes(2).toMillis())
                // 동일한 Collapse Key를 가진 메시지가 연속 도착 시, 마지막 메시지만 표시되도록 함.
                .setCollapseKey(topic)
                // 메시지 중요도. HIGH는 즉시 전송을 의미.
                .setPriority(AndroidConfig.Priority.HIGH)
                // 안드로이드 알림의 시각/청각적 요소 설정
                .setNotification(AndroidNotification.builder()
//                        .setTitle(dataSource.getTitle()) 추후 andorid 전용 title이나 body 필요하면 사용.
//                        .setBody(dataSource.getBody()) Message의 setNotification에서 설정하더라도 이것이 우선됌.
                        .setSound(sound) // 'config'가 비어있으면 기본값이 사용됨
                        .setColor(color) // 'config'가 비어있으면 기본값이 사용됨
                        .setIcon(icon)   // 'config'가 비어있으면 기본값이 사용됨
                        .setTag(topic)
                        .setChannelId(NotificationParameter.ANDROID_CHANNEL_ID.getValue())
                        .build())
                .build();
    }

    /**
     * iOS 기기를 위한 APNS(Apple Push Notification Service) 상세 설정을 생성합니다.
     * @return 구성된 ApnsConfig 객체
     */
    private ApnsConfig getApnsConfig(PushNotificationDataSource dataSource) {
        String topic = dataSource.getTopic();

        // apns가 null이면, '빈 맵'을 대신 사용합니다.
        Map<String, String> config = (dataSource.getApns() != null)
                ? dataSource.getApns()
                : Collections.emptyMap();

        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setAlert(ApsAlert.builder()
                                .setTitle(dataSource.getTitle())
                                .setBody(dataSource.getBody())
                                .build())
                        .setCategory(topic) // 알림 카테고리 설정
                        .setThreadId(topic) // 동일한 ID를 가진 알림을 그룹화
                        .build())
                .build();
    }

    /**
     * 모든 Message 객체 생성에 필요한 공통 설정을 담은 Message.Builder를 생성하여 반환합니다.
     *
     * @param dataSource 공통적으로 필요한 제목, 내용이 포함된 요청 DTO
     * @return 안드로이드/iOS 공통 설정 및 알림 내용이 설정된 Message.Builder 객체
     */
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationDataSource dataSource) {
        // 공통 설정 빌드
        CommonNotificationConfig config = buildCommonConfig(dataSource);

        // 모든 메시지에 공통적으로 적용될 설정을 빌더에 추가합니다.
        return Message.builder()
                .putAllData(config.data())
                .setApnsConfig(config.apnsConfig())
                .setAndroidConfig(config.androidConfig())
                .setNotification(config.notification());
    }

    /**
     * 모든 Message 객체 생성에 필요한 공통 설정을 담은 MulticastMessage.Builder를 생성하여 반환합니다.
     *
     * @param dataSource 공통적으로 필요한 제목, 내용이 포함된 요청 DTO
     * @return 안드로이드/iOS 공통 설정 및 알림 내용이 설정된 MulticastMessage.Builder 객체
     */
    private MulticastMessage.Builder getPreconfiguredMulticastMessageBuilder(PushNotificationDataSource dataSource) {
        // 공통 설정 빌드
        CommonNotificationConfig config = buildCommonConfig(dataSource);

        // 모든 메시지에 공통적으로 적용될 설정을 빌더에 추가합니다.
        return MulticastMessage.builder()
                .putAllData(config.data())
                .setApnsConfig(config.apnsConfig())
                .setAndroidConfig(config.androidConfig())
                .setNotification(config.notification());
    }

    /**
     * Message / MulticastMessage 빌더에 공통으로 적용될
     * 설정값들을 보관하는 내부 래퍼(Wrapper) 객체입니다.
     */
    private record CommonNotificationConfig(
            AndroidConfig androidConfig,
            ApnsConfig apnsConfig,
            Notification notification,
            Map<String, String> data
    ) {}

    /**
     * dataSource를 기반으로 모든 공통 설정 객체(Config, Notification, Data)를 생성합니다.
     * @param dataSource 알림의 재료 (Request 또는 Event)
     * @return 공통 설정값이 담긴 CommonNotificationConfig 래퍼 객체
     */
    private CommonNotificationConfig buildCommonConfig(PushNotificationDataSource dataSource) {
        // 1. 기존 헬퍼 메서드 호출
        AndroidConfig androidConfig = getAndroidConfig(dataSource);
        ApnsConfig apnsConfig = getApnsConfig(dataSource);

        // 2. data 맵 null 처리
        Map<String, String> data = (dataSource.getData() != null)
                ? dataSource.getData()
                : Collections.emptyMap();

        // 3. Notification 객체 생성
        Notification notification = Notification.builder()
                .setTitle(dataSource.getTitle())
                .setBody(dataSource.getBody())
                .build();

        // 4. 래퍼 객체에 담아 반환
        return new CommonNotificationConfig(androidConfig, apnsConfig, notification, data);
    }
}
