package com.Rootale.fcm.entity;

import com.Rootale.member.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_topic")
public class UserTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Topic topic;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Builder
    public UserTopic(User user, Topic topic) {
        this.user = user;
        this.topic = topic;
        this.isActive = true; // 구독 시 기본 활성화
    }

}
