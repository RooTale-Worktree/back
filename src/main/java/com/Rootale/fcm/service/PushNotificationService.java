package com.Rootale.fcm.service;

import com.Rootale.fcm.NotificationParameter;
import com.Rootale.fcm.repository.FCMTokenRepository;
import com.Rootale.fcm.entity.Topic;
import com.Rootale.fcm.web.dto.PushNotificationEvent;
import com.Rootale.fcm.web.dto.PushNotificationRequest;
import com.Rootale.fcm.web.dto.SubscribeRequest;
import com.Rootale.fcm.entity.UserTopic;
import com.Rootale.member.entity.User;
import com.Rootale.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PushNotificationService {
    private final FCMTokenRepository fcmTokenRepository;
    private final FCMService fcmService;
    private final UserTopicService userTopicService;
    private final UserRepository userRepository;
    private final TopicService topicService;

    @Value("#{${app.notifications.samples}}")
    private Map<String, String> samples;
    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    /**
     * User의 모든 기기에 알람을 보내는 메서드
     * @param event 알람이 발생하는 event에서 담아주는 정보
     */
    public void sendPushNotificationToUser(PushNotificationEvent event) {

        try {
            List<String> deviceTokens = fcmTokenRepository.findValidAndEnabledDeviceTokensByUserId(event.getUserId());
            fcmService.sendMessageToMultipleToken(deviceTokens, event);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 해당 topic을 구독하는 유저 or 토큰에게 알림을 전송하는 메서드
     * @param request 추후 event로 변경 예정입니다
     */
    public void sendPushNotificationToTopic(PushNotificationRequest request) {
        try {

            List<String> deviceTokens = fcmTokenRepository.findActiveDeviceTokensByTopic(request.getTopic());
            fcmService.sendMessageToMultipleToken(deviceTokens, request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    /*
    개발자 테스트 or 관리자용 메소드입니다.
     */
    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void subscribeTopic(SubscribeRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            Topic topic = topicService.findByName(request.getTopic());

            UserTopic newSubscription = UserTopic.builder()
                    .user(user)
                    .topic(topic)
                    .build();
            userTopicService.save(newSubscription);

            List<String> deviceTokens = fcmTokenRepository.findValidAndEnabledDeviceTokensByUserId(request.getUserId());
            fcmService.subscribeTopic(deviceTokens, request.getTopic());
            // 엔티티 업데이트 로직
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    @Transactional
    public void unsubscribeTopic(SubscribeRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            Topic topic = topicService.findByName(request.getTopic());

            userTopicService.delete(user, topic);

            List<String> deviceTokens = fcmTokenRepository.findValidAndEnabledDeviceTokensByUserId(request.getUserId());
            fcmService.unsubscribeTopic(deviceTokens, request.getTopic());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    /*
    이 밑으로는 private로 선언되어 클래스 내부에서만 사용되는 헬퍼(Helper) 메소드입니다.
     */
    private Map<String, String> getDefaultPayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("click_action", NotificationParameter.DEFAULT_NOTIFICATION_CLICK.getValue());
        pushData.put("text", samples.get("payloadData") + " " + LocalDateTime.now());
        return pushData;
    }
}
