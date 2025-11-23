package com.Rootale.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//user_phone으로 알림 보내기 위한 고유token
@Entity
@Table(name = "fcmToken")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String deviceToken;

    private boolean isActive;

    private boolean notificationEnabled;

    private LocalDateTime tokenCheckTime;
}
