package com.LiveToon.model;

import java.net.URI;
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
 * Character
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class Character {

  private @Nullable UUID id;

  private @Nullable UUID worldId;

  private @Nullable String name;

  private @Nullable String description;

  private @Nullable String personality;

  private @Nullable String appearance;

  private @Nullable URI avatarUrl;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  public Character id(@Nullable UUID id) {
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

  public Character worldId(@Nullable UUID worldId) {
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

  public Character name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @Size(min = 1, max = 50) 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public Character description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @Size(max = 500) 
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public Character personality(@Nullable String personality) {
    this.personality = personality;
    return this;
  }

  /**
   * Get personality
   * @return personality
   */
  @Size(max = 1000) 
  @Schema(name = "personality", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("personality")
  public @Nullable String getPersonality() {
    return personality;
  }

  public void setPersonality(@Nullable String personality) {
    this.personality = personality;
  }

  public Character appearance(@Nullable String appearance) {
    this.appearance = appearance;
    return this;
  }

  /**
   * Get appearance
   * @return appearance
   */
  @Size(max = 1000) 
  @Schema(name = "appearance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("appearance")
  public @Nullable String getAppearance() {
    return appearance;
  }

  public void setAppearance(@Nullable String appearance) {
    this.appearance = appearance;
  }

  public Character avatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
    return this;
  }

  /**
   * Get avatarUrl
   * @return avatarUrl
   */
  @Valid 
  @Schema(name = "avatar_url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("avatar_url")
  public @Nullable URI getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public Character createdAt(@Nullable OffsetDateTime createdAt) {
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
    Character character = (Character) o;
    return Objects.equals(this.id, character.id) &&
        Objects.equals(this.worldId, character.worldId) &&
        Objects.equals(this.name, character.name) &&
        Objects.equals(this.description, character.description) &&
        Objects.equals(this.personality, character.personality) &&
        Objects.equals(this.appearance, character.appearance) &&
        Objects.equals(this.avatarUrl, character.avatarUrl) &&
        Objects.equals(this.createdAt, character.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, worldId, name, description, personality, appearance, avatarUrl, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Character {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    worldId: ").append(toIndentedString(worldId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    personality: ").append(toIndentedString(personality)).append("\n");
    sb.append("    appearance: ").append(toIndentedString(appearance)).append("\n");
    sb.append("    avatarUrl: ").append(toIndentedString(avatarUrl)).append("\n");
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

