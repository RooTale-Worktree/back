package com.LiveToon.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Scene
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class Scene {

  private @Nullable UUID id;

  private @Nullable UUID storyId;

  private @Nullable String title;

  private @Nullable String content;

  private @Nullable URI imageUrl;

  @Valid
  private List<@Valid Choice> choices = new ArrayList<>();

  private @Nullable Boolean isFinal;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  public Scene id(@Nullable UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable UUID getId() {
    return id;
  }

  public void setId(@Nullable UUID id) {
    this.id = id;
  }

  public Scene storyId(@Nullable UUID storyId) {
    this.storyId = storyId;
    return this;
  }

  /**
   * Get storyId
   * @return storyId
   */
  @Valid 
  @Schema(name = "story_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("story_id")
  public @Nullable UUID getStoryId() {
    return storyId;
  }

  public void setStoryId(@Nullable UUID storyId) {
    this.storyId = storyId;
  }

  public Scene title(@Nullable String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   */
  
  @Schema(name = "title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public @Nullable String getTitle() {
    return title;
  }

  public void setTitle(@Nullable String title) {
    this.title = title;
  }

  public Scene content(@Nullable String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   */
  
  @Schema(name = "content", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public @Nullable String getContent() {
    return content;
  }

  public void setContent(@Nullable String content) {
    this.content = content;
  }

  public Scene imageUrl(@Nullable URI imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * Get imageUrl
   * @return imageUrl
   */
  @Valid 
  @Schema(name = "image_url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image_url")
  public @Nullable URI getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(@Nullable URI imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Scene choices(List<@Valid Choice> choices) {
    this.choices = choices;
    return this;
  }

  public Scene addChoicesItem(Choice choicesItem) {
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

  public Scene isFinal(@Nullable Boolean isFinal) {
    this.isFinal = isFinal;
    return this;
  }

  /**
   * Get isFinal
   * @return isFinal
   */
  
  @Schema(name = "is_final", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("is_final")
  public @Nullable Boolean getIsFinal() {
    return isFinal;
  }

  public void setIsFinal(@Nullable Boolean isFinal) {
    this.isFinal = isFinal;
  }

  public Scene createdAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @Valid 
  @Schema(name = "created_at", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("created_at")
  public @Nullable OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Scene scene = (Scene) o;
    return Objects.equals(this.id, scene.id) &&
        Objects.equals(this.storyId, scene.storyId) &&
        Objects.equals(this.title, scene.title) &&
        Objects.equals(this.content, scene.content) &&
        Objects.equals(this.imageUrl, scene.imageUrl) &&
        Objects.equals(this.choices, scene.choices) &&
        Objects.equals(this.isFinal, scene.isFinal) &&
        Objects.equals(this.createdAt, scene.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, storyId, title, content, imageUrl, choices, isFinal, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Scene {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    storyId: ").append(toIndentedString(storyId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
    sb.append("    choices: ").append(toIndentedString(choices)).append("\n");
    sb.append("    isFinal: ").append(toIndentedString(isFinal)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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

