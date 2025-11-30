package com.Rootale.universe.entity;

import com.Rootale.universe.dto.LlmDto;
import com.Rootale.universe.exception.JsonDeserializationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    @Property("character_id")
    private String characterId;

    @Property("name")
    private String name;

    @Property("description")
    private String description;

    @Property("constraints")
    private List<String> constraints;

    @Property("affiliation")
    private String affiliation;

    // 직렬화된 JSON 문자열로 저장
    @Property("example_dialogue")
    private String exampleDialogue;

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

    /**
     * JSON 문자열로 저장된 example_dialogue를 List<ExampleDialogueDto>로 변환
     */
    public List<LlmDto.ExampleDialogueDto> getExampleDialogueList() {
        if (exampleDialogue == null || exampleDialogue.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(
                    exampleDialogue,
                    new TypeReference<List<LlmDto.ExampleDialogueDto>>() {}
            );
        } catch (Exception e) {
            throw new JsonDeserializationException(
                    "Failed to deserialize example dialogue for character: " + this.characterId, e);
        }
    }
}