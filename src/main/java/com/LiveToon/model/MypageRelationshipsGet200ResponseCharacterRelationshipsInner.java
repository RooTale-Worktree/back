package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageRelationshipsGet200ResponseCharacterRelationshipsInner
 */

@JsonTypeName("_mypage_relationships_get_200_response_character_relationships_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageRelationshipsGet200ResponseCharacterRelationshipsInner {

  private @Nullable Character character;

  private @Nullable Integer relationshipLevel;

  private @Nullable Integer interactionCount;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime lastInteraction;

  @Valid
  private List<String> favoriteTopics = new ArrayList<>();

  public MypageRelationshipsGet200ResponseCharacterRelationshipsInner character(@Nullable Character character) {
    this.character = character;
    return this;
  }

  /**
   * Get character
   * @return character
   */
  @Valid 
  @Schema(name = "character", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("character")
  public @Nullable Character getCharacter() {
    return character;
  }

  public void setCharacter(@Nullable Character character) {
    this.character = character;
  }

  public MypageRelationshipsGet200ResponseCharacterRelationshipsInner relationshipLevel(@Nullable Integer relationshipLevel) {
    this.relationshipLevel = relationshipLevel;
    return this;
  }

  /**
   * 愿�怨� �섏� (0-100)
   * minimum: 0
   * maximum: 100
   * @return relationshipLevel
   */
  @Min(0) @Max(100) 
  @Schema(name = "relationship_level", description = "愿�怨� �섏� (0-100)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("relationship_level")
  public @Nullable Integer getRelationshipLevel() {
    return relationshipLevel;
  }

  public void setRelationshipLevel(@Nullable Integer relationshipLevel) {
    this.relationshipLevel = relationshipLevel;
  }

  public MypageRelationshipsGet200ResponseCharacterRelationshipsInner interactionCount(@Nullable Integer interactionCount) {
    this.interactionCount = interactionCount;
    return this;
  }

  /**
   * �곹샇�묒슜 �잛닔
   * @return interactionCount
   */
  
  @Schema(name = "interaction_count", description = "�곹샇�묒슜 �잛닔", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("interaction_count")
  public @Nullable Integer getInteractionCount() {
    return interactionCount;
  }

  public void setInteractionCount(@Nullable Integer interactionCount) {
    this.interactionCount = interactionCount;
  }

  public MypageRelationshipsGet200ResponseCharacterRelationshipsInner lastInteraction(@Nullable OffsetDateTime lastInteraction) {
    this.lastInteraction = lastInteraction;
    return this;
  }

  /**
   * Get lastInteraction
   * @return lastInteraction
   */
  @Valid 
  @Schema(name = "last_interaction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("last_interaction")
  public @Nullable OffsetDateTime getLastInteraction() {
    return lastInteraction;
  }

  public void setLastInteraction(@Nullable OffsetDateTime lastInteraction) {
    this.lastInteraction = lastInteraction;
  }

  public MypageRelationshipsGet200ResponseCharacterRelationshipsInner favoriteTopics(List<String> favoriteTopics) {
    this.favoriteTopics = favoriteTopics;
    return this;
  }

  public MypageRelationshipsGet200ResponseCharacterRelationshipsInner addFavoriteTopicsItem(String favoriteTopicsItem) {
    if (this.favoriteTopics == null) {
      this.favoriteTopics = new ArrayList<>();
    }
    this.favoriteTopics.add(favoriteTopicsItem);
    return this;
  }

  /**
   * Get favoriteTopics
   * @return favoriteTopics
   */
  
  @Schema(name = "favorite_topics", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("favorite_topics")
  public List<String> getFavoriteTopics() {
    return favoriteTopics;
  }

  public void setFavoriteTopics(List<String> favoriteTopics) {
    this.favoriteTopics = favoriteTopics;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageRelationshipsGet200ResponseCharacterRelationshipsInner mypageRelationshipsGet200ResponseCharacterRelationshipsInner = (MypageRelationshipsGet200ResponseCharacterRelationshipsInner) o;
    return Objects.equals(this.character, mypageRelationshipsGet200ResponseCharacterRelationshipsInner.character) &&
        Objects.equals(this.relationshipLevel, mypageRelationshipsGet200ResponseCharacterRelationshipsInner.relationshipLevel) &&
        Objects.equals(this.interactionCount, mypageRelationshipsGet200ResponseCharacterRelationshipsInner.interactionCount) &&
        Objects.equals(this.lastInteraction, mypageRelationshipsGet200ResponseCharacterRelationshipsInner.lastInteraction) &&
        Objects.equals(this.favoriteTopics, mypageRelationshipsGet200ResponseCharacterRelationshipsInner.favoriteTopics);
  }

  @Override
  public int hashCode() {
    return Objects.hash(character, relationshipLevel, interactionCount, lastInteraction, favoriteTopics);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageRelationshipsGet200ResponseCharacterRelationshipsInner {\n");
    sb.append("    character: ").append(toIndentedString(character)).append("\n");
    sb.append("    relationshipLevel: ").append(toIndentedString(relationshipLevel)).append("\n");
    sb.append("    interactionCount: ").append(toIndentedString(interactionCount)).append("\n");
    sb.append("    lastInteraction: ").append(toIndentedString(lastInteraction)).append("\n");
    sb.append("    favoriteTopics: ").append(toIndentedString(favoriteTopics)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

