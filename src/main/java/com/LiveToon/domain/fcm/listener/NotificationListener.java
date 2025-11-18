package com.LiveToon.domain.fcm.listener;

import com.LiveToon.domain.fcm.service.PushNotificationService;
import com.LiveToon.domain.fcm.web.dto.PushNotificationEvent;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final PushNotificationService pushNotificationService;


    @Async // 별도의 스레드에서 비동기로 실행
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // 트랜잭션이 '커밋'된 후에만 실행
    public void handlePushNotificationEvent(PushNotificationEvent event) {
        pushNotificationService.sendPushNotificationToUser(event);
    }
}