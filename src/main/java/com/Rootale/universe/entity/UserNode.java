package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNode {

    @Id
    @Property("user_id")
    private Integer userId;  // RDB의 users 테이블 PK

    @Relationship(type = "PLAY", direction = Relationship.Direction.OUTGOING)
    private List<PlayRelationship> playRelationships = new ArrayList<>();

    @Relationship(type = "INTERACT", direction = Relationship.Direction.OUTGOING)
    private List<InteractRelationship> interactRelationships = new ArrayList<>();
}