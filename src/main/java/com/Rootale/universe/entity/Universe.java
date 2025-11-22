package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Node("Universe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Universe {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String universeId;

    @Property("name")
    private String name;

    @Property("story")
    private String story;

    @Property("canon")
    private String canon;

    @Property("description")
    private String description;

    @Property("estimated_play_time")
    private Integer estimatedPlayTime;

    @Property("representative_image")
    private String representativeImage;

    @Property("created_at")
    private LocalDateTime createdAt;

    @Property("updated_at")
    private LocalDateTime updatedAt;

    @Relationship(type = "HAS_START", direction = Relationship.Direction.OUTGOING)
    private Scene startScene;

    @Relationship(type = "HAS_CHARACTER", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private List<Character> characters = new ArrayList<>();

}