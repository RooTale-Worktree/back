package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * MypageStoriesGet200ResponseStoriesInner
 */

@JsonTypeName("_mypage_stories_get_200_response_stories_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageStoriesGet200ResponseStoriesInner {

  private @Nullable UUID id;

  private @Nullable UUID worldId;

  private @Nullable String title;

  private @Nullable String description;

  private @Nullable UUID currentSceneId;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    ACTIVE("active"),
    
    PAUSED("paused"),
    
    COMPLETED("completed"),
    
    ABANDONED("abandoned");

    private final String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable StatusEnum status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  private @Nullable Integer progressPercentage;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime lastPlayed;

  private @Nullable String worldName;

  public MypageStoriesGet200ResponseStoriesInner id(@Nullable UUID id) {
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

  public MypageStoriesGet200ResponseStoriesInner worldId(@Nullable UUID worldId) {
    this.worldId = worldId;
    return this;
  }

  /**
   * Get worldId
   * @return worldId
   */
  @Valid 
  @Schema(name = "world_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("world_id")
  public @Nullable UUID getWorldId() {
    return worldId;
  }

  public void setWorldId(@Nullable UUID worldId) {
    this.worldId = worldId;
  }

  public MypageStoriesGet200ResponseStoriesInner title(@Nullable String title) {
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

  public MypageStoriesGet200ResponseStoriesInner description(@Nullable String description) {
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

  public MypageStoriesGet200ResponseStoriesInner currentSceneId(@Nullable UUID currentSceneId) {
    this.currentSceneId = currentSceneId;
    return this;
  }

  /**
   * Get currentSceneId
   * @return currentSceneId
   */
  @Valid 
  @Schema(name = "current_scene_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("current_scene_id")
  public @Nullable UUID getCurrentSceneId() {
    return currentSceneId;
  }

  public void setCurrentSceneId(@Nullable UUID currentSceneId) {
    this.currentSceneId = currentSceneId;
  }

  public MypageStoriesGet200ResponseStoriesInner status(@Nullable StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public @Nullable StatusEnum getStatus() {
    return status;
  }

  public void setStatus(@Nullable StatusEnum status) {
    this.status = status;
  }

  public MypageStoriesGet200ResponseStoriesInner createdAt(@Nullable OffsetDateTime createdAt) {
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

  public MypageStoriesGet200ResponseStoriesInner updatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updated_at", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updated_at")
  public @Nullable OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public MypageStoriesGet200ResponseStoriesInner progressPercentage(@Nullable Integer progressPercentage) {
    this.progressPercentage = progressPercentage;
    return this;
  }

  /**
   * Get progressPercentage
   * minimum: 0
   * maximum: 100
   * @return progressPercentage
   */
  @Min(0) @Max(100) 
  @Schema(name = "progress_percentage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("progress_percentage")
  public @Nullable Integer getProgressPercentage() {
    return progressPercentage;
  }

  public void setProgressPercentage(@Nullable Integer progressPercentage) {
    this.progressPercentage = progressPercentage;
  }

  public MypageStoriesGet200ResponseStoriesInner lastPlayed(@Nullable OffsetDateTime lastPlayed) {
    this.lastPlayed = lastPlayed;
    return this;
  }

  /**
   * Get lastPlayed
   * @return lastPlayed
   */
  @Valid 
  @Schema(name = "last_played", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("last_played")
  public @Nullable OffsetDateTime getLastPlayed() {
    return lastPlayed;
  }

  public void setLastPlayed(@Nullable OffsetDateTime lastPlayed) {
    this.lastPlayed = lastPlayed;
  }

  public MypageStoriesGet200ResponseStoriesInner worldName(@Nullable String worldName) {
    this.worldName = worldName;
    return this;
  }

  /**
   * Get worldName
   * @return worldName
   */
  
  @Schema(name = "world_name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("world_name")
  public @Nullable String getWorldName() {
    return worldName;
  }

  public void setWorldName(@Nullable String worldName) {
    this.worldName = worldName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageStoriesGet200ResponseStoriesInner mypageStoriesGet200ResponseStoriesInner = (MypageStoriesGet200ResponseStoriesInner) o;
    return Objects.equals(this.id, mypageStoriesGet200ResponseStoriesInner.id) &&
        Objects.equals(this.worldId, mypageStoriesGet200ResponseStoriesInner.worldId) &&
        Objects.equals(this.title, mypageStoriesGet200ResponseStoriesInner.title) &&
        Objects.equals(this.description, mypageStoriesGet200ResponseStoriesInner.description) &&
        Objects.equals(this.currentSceneId, mypageStoriesGet200ResponseStoriesInner.currentSceneId) &&
        Objects.equals(this.status, mypageStoriesGet200ResponseStoriesInner.status) &&
        Objects.equals(this.createdAt, mypageStoriesGet200ResponseStoriesInner.createdAt) &&
        Objects.equals(this.updatedAt, mypageStoriesGet200ResponseStoriesInner.updatedAt) &&
        Objects.equals(this.progressPercentage, mypageStoriesGet200ResponseStoriesInner.progressPercentage) &&
        Objects.equals(this.lastPlayed, mypageStoriesGet200ResponseStoriesInner.lastPlayed) &&
        Objects.equals(this.worldName, mypageStoriesGet200ResponseStoriesInner.worldName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, worldId, title, description, currentSceneId, status, createdAt, updatedAt, progressPercentage, lastPlayed, worldName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageStoriesGet200ResponseStoriesInner {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    worldId: ").append(toIndentedString(worldId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    currentSceneId: ").append(toIndentedString(currentSceneId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    progressPercentage: ").append(toIndentedString(progressPercentage)).append("\n");
    sb.append("    lastPlayed: ").append(toIndentedString(lastPlayed)).append("\n");
    sb.append("    worldName: ").append(toIndentedString(worldName)).append("\n");
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

