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
 * WorldsWorldIdStartPost201Response
 */

@JsonTypeName("_worlds__world_id__start_post_201_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class WorldsWorldIdStartPost201Response {

  private @Nullable Story story;

  private @Nullable Scene firstScene;

  public WorldsWorldIdStartPost201Response story(@Nullable Story story) {
    this.story = story;
    return this;
  }

  /**
   * Get story
   * @return story
   */
  @Valid 
  @Schema(name = "story", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("story")
  public @Nullable Story getStory() {
    return story;
  }

  public void setStory(@Nullable Story story) {
    this.story = story;
  }

  public WorldsWorldIdStartPost201Response firstScene(@Nullable Scene firstScene) {
    this.firstScene = firstScene;
    return this;
  }

  /**
   * Get firstScene
   * @return firstScene
   */
  @Valid 
  @Schema(name = "first_scene", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("first_scene")
  public @Nullable Scene getFirstScene() {
    return firstScene;
  }

  public void setFirstScene(@Nullable Scene firstScene) {
    this.firstScene = firstScene;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorldsWorldIdStartPost201Response worldsWorldIdStartPost201Response = (WorldsWorldIdStartPost201Response) o;
    return Objects.equals(this.story, worldsWorldIdStartPost201Response.story) &&
        Objects.equals(this.firstScene, worldsWorldIdStartPost201Response.firstScene);
  }

  @Override
  public int hashCode() {
    return Objects.hash(story, firstScene);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorldsWorldIdStartPost201Response {\n");
    sb.append("    story: ").append(toIndentedString(story)).append("\n");
    sb.append("    firstScene: ").append(toIndentedString(firstScene)).append("\n");
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

