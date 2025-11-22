package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @Property("last_read_node_id")
    private String lastReadNodeId;

    @Property("progress")
    private Float progress;

    @TargetNode
    private Universe universe;
}