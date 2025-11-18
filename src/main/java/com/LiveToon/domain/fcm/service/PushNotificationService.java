package com.LiveToon.domain.fcm.service;

import com.LiveToon.domain.fcm.NotificationParameter;
import com.LiveToon.domain.fcm.repository.FCMTokenRepository;
import com.LiveToon.domain.topic.entity.Topic;
import com.LiveToon.domain.topic.repository.TopicRepository;
import com.LiveToon.domain.fcm.web.dto.PushNotificationEvent;
import com.LiveToon.domain.fcm.web.dto.PushNotificationRequest;
import com.LiveToon.domain.fcm.web.dto.SubscribeRequest;
import com.LiveToon.domain.topic.service.TopicService;
import com.LiveToon.domain.user.entity.User;
import com.LiveToon.domain.user.entity.UserTopic;
import com.LiveToon.domain.user.repository.UserTopicRepository;
import com.LiveToon.domain.user.service.UserService;
import com.LiveToon.domain.user.service.UserTopicService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

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
    private final UserService userService;
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
            User user = userService.findUserById(request.getUserId());
            Topic topic = topicService.findByName(request.getTopic());

            UserTopic newSubscription = UserTopic.builder()
                    .user(user)
                    .topic(topic)
                    .build();
            userTopicService.save(newSubscription);

            List<String> deviceTokens = fcmTokenRepository.findValidAndEnabledDeviceTokensByUserId(request.getUserId());
            fcmService.subscribeTopic(deviceTokens, request.getTopic());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    @Transactional
    public void unsubscribeTopic(SubscribeRequest request) {
        try {
            User user = userService.findUserById(request.getUserId());
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
