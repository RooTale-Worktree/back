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
 * WorldsWorldIdCharactersGet200Response
 */

@JsonTypeName("_worlds__world_id__characters_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class WorldsWorldIdCharactersGet200Response {

  @Valid
  private List<@Valid Character> characters = new ArrayList<>();

  private @Nullable WorldsGet200ResponsePagination pagination;

  public WorldsWorldIdCharactersGet200Response characters(List<@Valid Character> characters) {
    this.characters = characters;
    return this;
  }

  public WorldsWorldIdCharactersGet200Response addCharactersItem(Character charactersItem) {
    if (this.characters == null) {
      this.characters = new ArrayList<>();
    }
    this.characters.add(charactersItem);
    return this;
  }

  /**
   * Get characters
   * @return characters
   */
  @Valid 
  @Schema(name = "characters", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("characters")
  public List<@Valid Character> getCharacters() {
    return characters;
  }

  public void setCharacters(List<@Valid Character> characters) {
    this.characters = characters;
  }

  public WorldsWorldIdCharactersGet200Response pagination(@Nullable WorldsGet200ResponsePagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   * @return pagination
   */
  @Valid 
  @Schema(name = "pagination", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pagination")
  public @Nullable WorldsGet200ResponsePagination getPagination() {
    return pagination;
  }

  public void setPagination(@Nullable WorldsGet200ResponsePagination pagination) {
    this.pagination = pagination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorldsWorldIdCharactersGet200Response worldsWorldIdCharactersGet200Response = (WorldsWorldIdCharactersGet200Response) o;
    return Objects.equals(this.characters, worldsWorldIdCharactersGet200Response.characters) &&
        Objects.equals(this.pagination, worldsWorldIdCharactersGet200Response.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(characters, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorldsWorldIdCharactersGet200Response {\n");
    sb.append("    characters: ").append(toIndentedString(characters)).append("\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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

