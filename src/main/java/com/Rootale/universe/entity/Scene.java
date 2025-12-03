
package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Node("Scene")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scene {

    @Id
    @Property("node_id")
    private String nodeId;

    @Property("title")
    private String title;

    @Property("description")
    private String description;

    @Property("setting")
    private String setting;

    @Property("phase")
    private String phase;

    @Property("characters")
    private String characters;

    @Property("character_state")
    private String characterState;

    @Property("purpose")
    private String purpose;

    @Property("created_at")
    private LocalDateTime createdAt;

    @Property("updated_at")
    private LocalDateTime updatedAt;

    @Relationship(type = "CHOICE", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private List<ChoiceRelationship> choices = new ArrayList<>();

    @Relationship(type = "PREREQUISITION", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private List<PrerequisitionRelationship> prerequisitions = new ArrayList<>();
}