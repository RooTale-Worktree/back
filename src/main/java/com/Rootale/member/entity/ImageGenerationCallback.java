package com.Rootale.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "image_generation_callbacks")
public class ImageGenerationCallback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "callback_id")
    private UUID callbackId;

    @Column(name = "session_id", nullable = false, length = 36)
    private String sessionId;

    @Column(name = "message_id", length = 36)
    private UUID messageId;

    @Column(name = "status", nullable = false, length = 20)
    private String status;  // pending, completed, failed

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "error", columnDefinition = "TEXT")
    private String error;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}