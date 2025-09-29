package com.LiveToon.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * WorldsWorldIdCharactersPostRequest
 */

@JsonTypeName("_worlds__world_id__characters_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class WorldsWorldIdCharactersPostRequest {

  private String name;

  private @Nullable String description;

  private String personality;

  private String appearance;

  private @Nullable URI avatarUrl;

  public WorldsWorldIdCharactersPostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public WorldsWorldIdCharactersPostRequest(String name, String personality, String appearance) {
    this.name = name;
    this.personality = personality;
    this.appearance = appearance;
  }

  public WorldsWorldIdCharactersPostRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 罹먮┃�� �대쫫
   * @return name
   */
  @NotNull @Size(min = 1, max = 50) 
  @Schema(name = "name", description = "罹먮┃�� �대쫫", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WorldsWorldIdCharactersPostRequest description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * 罹먮┃�� �ㅻ챸
   * @return description
   */
  @Size(max = 500) 
  @Schema(name = "description", description = "罹먮┃�� �ㅻ챸", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public WorldsWorldIdCharactersPostRequest personality(String personality) {
    this.personality = personality;
    return this;
  }

  /**
   * 罹먮┃�� �깃꺽
   * @return personality
   */
  @NotNull @Size(max = 1000) 
  @Schema(name = "personality", description = "罹먮┃�� �깃꺽", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("personality")
  public String getPersonality() {
    return personality;
  }

  public void setPersonality(String personality) {
    this.personality = personality;
  }

  public WorldsWorldIdCharactersPostRequest appearance(String appearance) {
    this.appearance = appearance;
    return this;
  }

  /**
   * 罹먮┃�� �몃え
   * @return appearance
   */
  @NotNull @Size(max = 1000) 
  @Schema(name = "appearance", description = "罹먮┃�� �몃え", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("appearance")
  public String getAppearance() {
    return appearance;
  }

  public void setAppearance(String appearance) {
    this.appearance = appearance;
  }

  public WorldsWorldIdCharactersPostRequest avatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
    return this;
  }

  /**
   * 罹먮┃�� �꾨컮�� �대�吏� URL
   * @return avatarUrl
   */
  @Valid 
  @Schema(name = "avatar_url", description = "罹먮┃�� �꾨컮�� �대�吏� URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("avatar_url")
  public @Nullable URI getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorldsWorldIdCharactersPostRequest worldsWorldIdCharactersPostRequest = (WorldsWorldIdCharactersPostRequest) o;
    return Objects.equals(this.name, worldsWorldIdCharactersPostRequest.name) &&
        Objects.equals(this.description, worldsWorldIdCharactersPostRequest.description) &&
        Objects.equals(this.personality, worldsWorldIdCharactersPostRequest.personality) &&
        Objects.equals(this.appearance, worldsWorldIdCharactersPostRequest.appearance) &&
        Objects.equals(this.avatarUrl, worldsWorldIdCharactersPostRequest.avatarUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, personality, appearance, avatarUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorldsWorldIdCharactersPostRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    personality: ").append(toIndentedString(personality)).append("\n");
    sb.append("    appearance: ").append(toIndentedString(appearance)).append("\n");
    sb.append("    avatarUrl: ").append(toIndentedString(avatarUrl)).append("\n");
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

