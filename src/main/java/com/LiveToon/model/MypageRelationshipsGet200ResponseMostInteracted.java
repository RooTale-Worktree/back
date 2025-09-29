package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageRelationshipsGet200ResponseMostInteracted
 */

@JsonTypeName("_mypage_relationships_get_200_response_most_interacted")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageRelationshipsGet200ResponseMostInteracted {

  private @Nullable Character character;

  private @Nullable Integer interactionCount;

  public MypageRelationshipsGet200ResponseMostInteracted character(@Nullable Character character) {
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

  public MypageRelationshipsGet200ResponseMostInteracted interactionCount(@Nullable Integer interactionCount) {
    this.interactionCount = interactionCount;
    return this;
  }

  /**
   * Get interactionCount
   * @return interactionCount
   */
  
  @Schema(name = "interaction_count", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("interaction_count")
  public @Nullable Integer getInteractionCount() {
    return interactionCount;
  }

  public void setInteractionCount(@Nullable Integer interactionCount) {
    this.interactionCount = interactionCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageRelationshipsGet200ResponseMostInteracted mypageRelationshipsGet200ResponseMostInteracted = (MypageRelationshipsGet200ResponseMostInteracted) o;
    return Objects.equals(this.character, mypageRelationshipsGet200ResponseMostInteracted.character) &&
        Objects.equals(this.interactionCount, mypageRelationshipsGet200ResponseMostInteracted.interactionCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(character, interactionCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageRelationshipsGet200ResponseMostInteracted {\n");
    sb.append("    character: ").append(toIndentedString(character)).append("\n");
    sb.append("    interactionCount: ").append(toIndentedString(interactionCount)).append("\n");
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

