package com.Rootale.member.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable //복합key
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FollowsId implements Serializable {

    private Long followerId;
    private Long followingId;
}
