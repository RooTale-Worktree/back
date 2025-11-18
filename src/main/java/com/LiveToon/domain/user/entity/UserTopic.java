package com.LiveToon.domain.user.entity;

import com.LiveToon.domain.topic.entity.Topic;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic; // List<Topic> -> Topic

    @Column(name = "is_active", nullable = false) // (컬럼명은 is_active를 추천)
    private Boolean isActive = true;

    @Builder
    public UserTopic(User user, Topic topic) {
        this.user = user;
        this.topic = topic;
        this.isActive = true; // 구독 시 기본 활성화
    }

}
