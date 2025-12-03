package com.Rootale.universe.dto;

import com.Rootale.universe.entity.Scene;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SceneWithCandidatesDto {
    private Scene current;
    @JsonProperty("candidate_scenes")
    private List<Scene> candidateScenes;
    @JsonProperty("candidate_count")
    private Integer candidateCount;
    @JsonProperty("choice_relations")
    private List<ChoiceRelationshipDto> choiceRelations;
}