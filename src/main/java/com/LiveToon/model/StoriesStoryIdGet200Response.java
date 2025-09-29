package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * StoriesStoryIdGet200Response
 */

@JsonTypeName("_stories__story_id__get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class StoriesStoryIdGet200Response {

  private @Nullable Story story;

  private @Nullable Scene currentScene;

  @Valid
  private List<@Valid Scene> scenes = new ArrayList<>();

  public StoriesStoryIdGet200Response story(@Nullable Story story) {
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

  public StoriesStoryIdGet200Response currentScene(@Nullable Scene currentScene) {
    this.currentScene = currentScene;
    return this;
  }

  /**
   * Get currentScene
   * @return currentScene
   */
  @Valid 
  @Schema(name = "current_scene", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("current_scene")
  public @Nullable Scene getCurrentScene() {
    return currentScene;
  }

  public void setCurrentScene(@Nullable Scene currentScene) {
    this.currentScene = currentScene;
  }

  public StoriesStoryIdGet200Response scenes(List<@Valid Scene> scenes) {
    this.scenes = scenes;
    return this;
  }

  public StoriesStoryIdGet200Response addScenesItem(Scene scenesItem) {
    if (this.scenes == null) {
      this.scenes = new ArrayList<>();
    }
    this.scenes.add(scenesItem);
    return this;
  }

  /**
   * �λ㈃ 紐⑸줉 (include_scenes=true�� �뚮쭔)
   * @return scenes
   */
  @Valid 
  @Schema(name = "scenes", description = "�λ㈃ 紐⑸줉 (include_scenes=true�� �뚮쭔)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("scenes")
  public List<@Valid Scene> getScenes() {
    return scenes;
  }

  public void setScenes(List<@Valid Scene> scenes) {
    this.scenes = scenes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoriesStoryIdGet200Response storiesStoryIdGet200Response = (StoriesStoryIdGet200Response) o;
    return Objects.equals(this.story, storiesStoryIdGet200Response.story) &&
        Objects.equals(this.currentScene, storiesStoryIdGet200Response.currentScene) &&
        Objects.equals(this.scenes, storiesStoryIdGet200Response.scenes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(story, currentScene, scenes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoriesStoryIdGet200Response {\n");
    sb.append("    story: ").append(toIndentedString(story)).append("\n");
    sb.append("    currentScene: ").append(toIndentedString(currentScene)).append("\n");
    sb.append("    scenes: ").append(toIndentedString(scenes)).append("\n");
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

