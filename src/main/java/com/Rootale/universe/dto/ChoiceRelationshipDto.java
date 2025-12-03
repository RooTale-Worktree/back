package com.Rootale.universe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceRelationshipDto {

    private Long id;

    @JsonProperty("choice_text")
    private String choiceText;

    @JsonProperty("target_node_id")
    private String targetNodeId;
}