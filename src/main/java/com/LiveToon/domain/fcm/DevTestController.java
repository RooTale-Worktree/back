package com.LiveToon.domain.fcm;

import com.LiveToon.domain.fcm.web.dto.PushNotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile; // 1. Profile 임포트
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// "dev" 프로필일 때만 이 컨트롤러가 활성화됩니다.
@Profile("dev")
@RestController
public class DevTestController {

    // 이벤트 발행기를 직접 주입받습니다.
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Postman으로 이벤트를 강제로 발행시키는 테스트용 엔드포인트
     * (비즈니스 로직이 없어도 @EventListener를 테스트할 수 있음)
     */
    @PostMapping("/devTest/forceEvent")
    @Transactional
    public String forcePublishNotificationEvent(@RequestBody PushNotificationEvent event) {

        try {
            eventPublisher.publishEvent(event);
            return "OK: 이벤트 발행 성공. 비동기 리스너가 작업을 시작합니다.";

        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}