package com.Rootale.fcm.entity;

import com.Rootale.member.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//user에게 알림 보내기 위한 고유 token
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor @Getter @Setter
@Builder
@Table(name = "fcmToken")
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // User를 필요할 때만 조회(지연 로딩)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String deviceToken;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;

    // boolean = true, false | Boolean = true, false, null
    // 유저가 계정만 생성하고 알림 수신 여부 설정을 안하는 시나리오가 존재
    @Builder.Default
    @Column(nullable = false)
    private Boolean notificationEnabled = true;

    @Column(nullable = false)
    private LocalDateTime tokenCheckTime;

    @Builder
    public FcmToken(User user, String deviceToken) {
        this.user = user;
        this.deviceToken = deviceToken;
    }

    public void updateTokenCheckTime(LocalDateTime tokenCheckTime) {
        this.tokenCheckTime = tokenCheckTime;
    }
}
