package com.Rootale.member.entity;

import com.Rootale.member.enums.FeedbackCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User : Feedback = 1 : N (N 쪽이 FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
//    @JsonBackReference // User.feedbacks 와의 순환참조 방지
    private User user;

    @Column(nullable = false, length = 20)
    private String category;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
