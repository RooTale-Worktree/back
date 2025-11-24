package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

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
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String nodeId;

    @Property("universe_id")
    private String universeId;

    @Property("depth")
    private Integer depth;

    @Property("story_text")
    private String storyText;

    @Property("critique_score")
    private Integer critiqueScore;

    @Property("protagonist_state")
    private String protagonistState;

    @Property("choice_text")
    @Builder.Default
    private List<String> choiceText = new ArrayList<>();


    @Property("created_at")
    private LocalDateTime createdAt;

    @Property("updated_at")
    private LocalDateTime updatedAt;

    @Relationship(type = "CHOICE", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private List<ChoiceRelationship> choices = new ArrayList<>();

}