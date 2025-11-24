package com.Rootale.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "follows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follows {

    @EmbeddedId
    private FollowsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerId")                 // id.followerId 에 매핑
    @JoinColumn(name = "followerId")
    private User follower;                // 팔로우 하는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followingId")                // id.followingId 에 매핑
    @JoinColumn(name = "followingId")
    private User following;               // 팔로우 당하는 사람
}
