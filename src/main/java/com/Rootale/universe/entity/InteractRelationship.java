package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteractRelationship {

    @Id
    @GeneratedValue
    private Long id;

    // 보류 상태 - 추후 속성 추가 가능

    @TargetNode
    private Character character;
}