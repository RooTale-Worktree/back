package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * WorldsWorldIdStartPostRequest
 */

@JsonTypeName("_worlds__world_id__start_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class WorldsWorldIdStartPostRequest {

  private @Nullable UUID characterId;

  private @Nullable String storyTitle;

  public WorldsWorldIdStartPostRequest characterId(@Nullable UUID characterId) {
    this.characterId = characterId;
    return this;
  }

  /**
   * �ъ슜�� 罹먮┃�� ID
   * @return characterId
   */
  @Valid 
  @Schema(name = "character_id", description = "�ъ슜�� 罹먮┃�� ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("character_id")
  public @Nullable UUID getCharacterId() {
    return characterId;
  }

  public void setCharacterId(@Nullable UUID characterId) {
    this.characterId = characterId;
  }

  public WorldsWorldIdStartPostRequest storyTitle(@Nullable String storyTitle) {
    this.storyTitle = storyTitle;
    return this;
  }

  /**
   * �ㅽ넗由� �쒕ぉ (�좏깮�ы빆)
   * @return storyTitle
   */
  @Size(max = 100) 
  @Schema(name = "story_title", description = "�ㅽ넗由� �쒕ぉ (�좏깮�ы빆)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("story_title")
  public @Nullable String getStoryTitle() {
    return storyTitle;
  }

  public void setStoryTitle(@Nullable String storyTitle) {
    this.storyTitle = storyTitle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorldsWorldIdStartPostRequest worldsWorldIdStartPostRequest = (WorldsWorldIdStartPostRequest) o;
    return Objects.equals(this.characterId, worldsWorldIdStartPostRequest.characterId) &&
        Objects.equals(this.storyTitle, worldsWorldIdStartPostRequest.storyTitle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(characterId, storyTitle);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorldsWorldIdStartPostRequest {\n");
    sb.append("    characterId: ").append(toIndentedString(characterId)).append("\n");
    sb.append("    storyTitle: ").append(toIndentedString(storyTitle)).append("\n");
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

