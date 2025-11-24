package com.Rootale.fcm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Builder
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="topic", cascade= CascadeType.ALL, orphanRemoval= true)
    List<UserTopic> userTopics = new ArrayList<>();

    @Column(nullable = false)
    private String name;
}
