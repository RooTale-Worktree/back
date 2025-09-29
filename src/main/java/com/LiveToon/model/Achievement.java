package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Achievement
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class Achievement {

  private @Nullable UUID id;

  private @Nullable String name;

  private @Nullable String description;

  private @Nullable String category;

  private @Nullable Integer progress;

  private @Nullable Boolean isCompleted;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime unlockedAt;

  public Achievement id(@Nullable UUID id) {
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

  public Achievement name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public Achievement description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public Achievement category(@Nullable String category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
   */
  
  @Schema(name = "category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("category")
  public @Nullable String getCategory() {
    return category;
  }

  public void setCategory(@Nullable String category) {
    this.category = category;
  }

  public Achievement progress(@Nullable Integer progress) {
    this.progress = progress;
    return this;
  }

  /**
   * Get progress
   * minimum: 0
   * maximum: 100
   * @return progress
   */
  @Min(0) @Max(100) 
  @Schema(name = "progress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("progress")
  public @Nullable Integer getProgress() {
    return progress;
  }

  public void setProgress(@Nullable Integer progress) {
    this.progress = progress;
  }

  public Achievement isCompleted(@Nullable Boolean isCompleted) {
    this.isCompleted = isCompleted;
    return this;
  }

  /**
   * Get isCompleted
   * @return isCompleted
   */
  
  @Schema(name = "is_completed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("is_completed")
  public @Nullable Boolean getIsCompleted() {
    return isCompleted;
  }

  public void setIsCompleted(@Nullable Boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

  public Achievement unlockedAt(@Nullable OffsetDateTime unlockedAt) {
    this.unlockedAt = unlockedAt;
    return this;
  }

  /**
   * Get unlockedAt
   * @return unlockedAt
   */
  @Valid 
  @Schema(name = "unlocked_at", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unlocked_at")
  public @Nullable OffsetDateTime getUnlockedAt() {
    return unlockedAt;
  }

  public void setUnlockedAt(@Nullable OffsetDateTime unlockedAt) {
    this.unlockedAt = unlockedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Achievement achievement = (Achievement) o;
    return Objects.equals(this.id, achievement.id) &&
        Objects.equals(this.name, achievement.name) &&
        Objects.equals(this.description, achievement.description) &&
        Objects.equals(this.category, achievement.category) &&
        Objects.equals(this.progress, achievement.progress) &&
        Objects.equals(this.isCompleted, achievement.isCompleted) &&
        Objects.equals(this.unlockedAt, achievement.unlockedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, category, progress, isCompleted, unlockedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Achievement {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    progress: ").append(toIndentedString(progress)).append("\n");
    sb.append("    isCompleted: ").append(toIndentedString(isCompleted)).append("\n");
    sb.append("    unlockedAt: ").append(toIndentedString(unlockedAt)).append("\n");
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

