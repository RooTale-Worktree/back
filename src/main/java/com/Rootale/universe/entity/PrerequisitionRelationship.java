package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrerequisitionRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Scene candidateScene;

    // 필요한 추가 속성이 있다면 여기에 추가
}