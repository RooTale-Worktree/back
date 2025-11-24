package com.Rootale.universe.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Node("Character")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Character {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String characterId;

    @Property("name")
    private String name;

    @Property("description")
    private String description;

    @Property("avatar_url")
    private String avatarUrl;

    @Property("personality")
    @Builder.Default
    private List<String> personality = new ArrayList<>();

    @Property("universe_id")
    private String universeId;

    @Property("created_at")
    private LocalDateTime createdAt;

    @Property("updated_at")
    private LocalDateTime updatedAt;
}