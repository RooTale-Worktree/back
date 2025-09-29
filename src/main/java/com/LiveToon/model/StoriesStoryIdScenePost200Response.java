package com.LiveToon.model;

import java.net.URI;
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
 * StoriesStoryIdScenePost200Response
 */

@JsonTypeName("_stories__story_id__scene_post_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class StoriesStoryIdScenePost200Response {

  private @Nullable Scene scene;

  private @Nullable String prose;

  private @Nullable URI imageUrl;

  @Valid
  private List<@Valid Choice> choices = new ArrayList<>();

  public StoriesStoryIdScenePost200Response scene(@Nullable Scene scene) {
    this.scene = scene;
    return this;
  }

  /**
   * Get scene
   * @return scene
   */
  @Valid 
  @Schema(name = "scene", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("scene")
  public @Nullable Scene getScene() {
    return scene;
  }

  public void setScene(@Nullable Scene scene) {
    this.scene = scene;
  }

  public StoriesStoryIdScenePost200Response prose(@Nullable String prose) {
    this.prose = prose;
    return this;
  }

  /**
   * �앹꽦�� �λ㈃ �띿뒪��
   * @return prose
   */
  
  @Schema(name = "prose", description = "�앹꽦�� �λ㈃ �띿뒪��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prose")
  public @Nullable String getProse() {
    return prose;
  }

  public void setProse(@Nullable String prose) {
    this.prose = prose;
  }

  public StoriesStoryIdScenePost200Response imageUrl(@Nullable URI imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * �앹꽦�� �대�吏� URL
   * @return imageUrl
   */
  @Valid 
  @Schema(name = "image_url", description = "�앹꽦�� �대�吏� URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image_url")
  public @Nullable URI getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(@Nullable URI imageUrl) {
    this.imageUrl = imageUrl;
  }

  public StoriesStoryIdScenePost200Response choices(List<@Valid Choice> choices) {
    this.choices = choices;
    return this;
  }

  public StoriesStoryIdScenePost200Response addChoicesItem(Choice choicesItem) {
    if (this.choices == null) {
      this.choices = new ArrayList<>();
    }
    this.choices.add(choicesItem);
    return this;
  }

  /**
   * Get choices
   * @return choices
   */
  @Valid 
  @Schema(name = "choices", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("choices")
  public List<@Valid Choice> getChoices() {
    return choices;
  }

  public void setChoices(List<@Valid Choice> choices) {
    this.choices = choices;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoriesStoryIdScenePost200Response storiesStoryIdScenePost200Response = (StoriesStoryIdScenePost200Response) o;
    return Objects.equals(this.scene, storiesStoryIdScenePost200Response.scene) &&
        Objects.equals(this.prose, storiesStoryIdScenePost200Response.prose) &&
        Objects.equals(this.imageUrl, storiesStoryIdScenePost200Response.imageUrl) &&
        Objects.equals(this.choices, storiesStoryIdScenePost200Response.choices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scene, prose, imageUrl, choices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoriesStoryIdScenePost200Response {\n");
    sb.append("    scene: ").append(toIndentedString(scene)).append("\n");
    sb.append("    prose: ").append(toIndentedString(prose)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
    sb.append("    choices: ").append(toIndentedString(choices)).append("\n");
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

