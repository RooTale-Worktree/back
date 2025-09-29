package com.LiveToon.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * World
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class World {

  private @Nullable UUID id;

  private @Nullable String name;

  private @Nullable String description;

  /**
   * �멸퀎愿� �뚮쭏
   */
  public enum ThemeEnum {
    FANTASY("fantasy"),
    
    SCIFI("scifi"),
    
    MODERN("modern"),
    
    HISTORICAL("historical"),
    
    ROMANCE("romance"),
    
    HORROR("horror");

    private final String value;

    ThemeEnum(String value) {
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
    public static ThemeEnum fromValue(String value) {
      for (ThemeEnum b : ThemeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ThemeEnum theme;

  private @Nullable URI thumbnailUrl;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  public World id(@Nullable UUID id) {
    this.id = id;
    return this;
  }

  /**
   * �멸퀎愿� 怨좎쑀 ID
   * @return id
   */
  @Valid 
  @Schema(name = "id", description = "�멸퀎愿� 怨좎쑀 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable UUID getId() {
    return id;
  }

  public void setId(@Nullable UUID id) {
    this.id = id;
  }

  public World name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * �멸퀎愿� �대쫫
   * @return name
   */
  
  @Schema(name = "name", description = "�멸퀎愿� �대쫫", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public World description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * �멸퀎愿� �ㅻ챸
   * @return description
   */
  
  @Schema(name = "description", description = "�멸퀎愿� �ㅻ챸", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public World theme(@Nullable ThemeEnum theme) {
    this.theme = theme;
    return this;
  }

  /**
   * �멸퀎愿� �뚮쭏
   * @return theme
   */
  
  @Schema(name = "theme", description = "�멸퀎愿� �뚮쭏", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("theme")
  public @Nullable ThemeEnum getTheme() {
    return theme;
  }

  public void setTheme(@Nullable ThemeEnum theme) {
    this.theme = theme;
  }

  public World thumbnailUrl(@Nullable URI thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
    return this;
  }

  /**
   * �몃꽕�� �대�吏� URL
   * @return thumbnailUrl
   */
  @Valid 
  @Schema(name = "thumbnail_url", description = "�몃꽕�� �대�吏� URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("thumbnail_url")
  public @Nullable URI getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(@Nullable URI thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public World createdAt(@Nullable OffsetDateTime createdAt) {
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

  public World updatedAt(@Nullable OffsetDateTime updatedAt) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    World world = (World) o;
    return Objects.equals(this.id, world.id) &&
        Objects.equals(this.name, world.name) &&
        Objects.equals(this.description, world.description) &&
        Objects.equals(this.theme, world.theme) &&
        Objects.equals(this.thumbnailUrl, world.thumbnailUrl) &&
        Objects.equals(this.createdAt, world.createdAt) &&
        Objects.equals(this.updatedAt, world.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, theme, thumbnailUrl, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class World {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    theme: ").append(toIndentedString(theme)).append("\n");
    sb.append("    thumbnailUrl: ").append(toIndentedString(thumbnailUrl)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

