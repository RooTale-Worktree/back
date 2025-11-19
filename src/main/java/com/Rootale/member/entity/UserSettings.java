package com.Rootale.member.entity;

import com.Rootale.member.enums.ImageQuality;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "userSettings")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserSettings {

    @Id
    @Column(name = "userId")
    private Long userId;

    // 1 : 1 (공유 PK)
    @OneToOne
    @MapsId   // userId = user.id
    @JoinColumn(name = "userId")
    private User user;

    private String theme;
    private String language;

    private boolean notifyEmail;
    private boolean notifyPush;
    private boolean notifyStoryUpdates;

    private boolean profileVisibility;
    private boolean storySharing;

    private boolean autoSave;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ImageQuality imageQuality;
}

