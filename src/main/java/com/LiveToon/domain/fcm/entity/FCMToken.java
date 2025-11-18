package com.LiveToon.domain.fcm.entity;

import com.LiveToon.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "fcm_token")
@Entity
public class FCMToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // User를 필요할 때만 조회(지연 로딩)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column(name = "device_token", nullable = false, unique = true)
    private String deviceToken;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid = true;

    // boolean = true, false | Boolean = true, false, null
    // 유저가 계정만 생성하고 알림 수신 여부 설정을 안하는 시나리오가 존재
    @Column(name = "notification_enabled", nullable = false)
    private Boolean notificationEnabled = true;

    @Column(name = "token_check_time", nullable = false)
    private LocalDateTime tokenCheckTime;

    @Builder
    public FCMToken(User user, String deviceToken) {
        this.user = user;
        this.deviceToken = deviceToken;
    }

    public void updateTokenCheckTime(LocalDateTime tokenCheckTime) {
        this.tokenCheckTime = tokenCheckTime;
    }
}
