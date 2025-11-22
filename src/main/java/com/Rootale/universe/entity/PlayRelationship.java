package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;

@RelationshipProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayRelationship {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;  // session_id로 사용

    @Property("last_read_node_id")
    private String lastReadNodeId;

    @Property("progress")
    private Float progress;

    @Property("session_name")
    private String sessionName;

    @Property("character_id")
    private String characterId;  // 이 세션에서 선택한 캐릭터

    @Property("created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Property("updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @TargetNode
    private Universe universe;
}