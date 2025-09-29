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
 * WorldsGet200Response
 */

@JsonTypeName("_worlds_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class WorldsGet200Response {

  @Valid
  private List<@Valid World> worlds = new ArrayList<>();

  private @Nullable WorldsGet200ResponsePagination pagination;

  public WorldsGet200Response worlds(List<@Valid World> worlds) {
    this.worlds = worlds;
    return this;
  }

  public WorldsGet200Response addWorldsItem(World worldsItem) {
    if (this.worlds == null) {
      this.worlds = new ArrayList<>();
    }
    this.worlds.add(worldsItem);
    return this;
  }

  /**
   * Get worlds
   * @return worlds
   */
  @Valid 
  @Schema(name = "worlds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("worlds")
  public List<@Valid World> getWorlds() {
    return worlds;
  }

  public void setWorlds(List<@Valid World> worlds) {
    this.worlds = worlds;
  }

  public WorldsGet200Response pagination(@Nullable WorldsGet200ResponsePagination pagination) {
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
    WorldsGet200Response worldsGet200Response = (WorldsGet200Response) o;
    return Objects.equals(this.worlds, worldsGet200Response.worlds) &&
        Objects.equals(this.pagination, worldsGet200Response.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(worlds, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorldsGet200Response {\n");
    sb.append("    worlds: ").append(toIndentedString(worlds)).append("\n");
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

