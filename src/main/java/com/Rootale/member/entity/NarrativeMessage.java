package com.Rootale.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "narrative_messages", indexes = {
        @Index(name = "idx_narrative_session_id", columnList = "session_id"),
        @Index(name = "idx_narrative_created_at", columnList = "created_at")
})
public class NarrativeMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "session_id", nullable = false, length = 36)
    private String sessionId;

    @Column(name = "role", nullable = false, length = 20)
    private String role;  // user, assistant

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "audio_url", length = 500)
    private String audioUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}