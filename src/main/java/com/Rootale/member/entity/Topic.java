package com.Rootale.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User : Topic = 1 : N (N 쪽이 FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
//    @JsonBackReference // User.topics 와의 순환참조 방지
    private User user;

    @Column(nullable = false, length = 255)
    private String name;
}
