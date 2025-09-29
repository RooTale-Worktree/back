package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Choice
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class Choice {

  private @Nullable UUID id;

  private @Nullable String text;

  private @Nullable UUID nextSceneId;

  private @Nullable String consequence;

  public Choice id(@Nullable UUID id) {
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

  public Choice text(@Nullable String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
   */
  
  @Schema(name = "text", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("text")
  public @Nullable String getText() {
    return text;
  }

  public void setText(@Nullable String text) {
    this.text = text;
  }

  public Choice nextSceneId(@Nullable UUID nextSceneId) {
    this.nextSceneId = nextSceneId;
    return this;
  }

  /**
   * Get nextSceneId
   * @return nextSceneId
   */
  @Valid 
  @Schema(name = "next_scene_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("next_scene_id")
  public @Nullable UUID getNextSceneId() {
    return nextSceneId;
  }

  public void setNextSceneId(@Nullable UUID nextSceneId) {
    this.nextSceneId = nextSceneId;
  }

  public Choice consequence(@Nullable String consequence) {
    this.consequence = consequence;
    return this;
  }

  /**
   * Get consequence
   * @return consequence
   */
  
  @Schema(name = "consequence", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("consequence")
  public @Nullable String getConsequence() {
    return consequence;
  }

  public void setConsequence(@Nullable String consequence) {
    this.consequence = consequence;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Choice choice = (Choice) o;
    return Objects.equals(this.id, choice.id) &&
        Objects.equals(this.text, choice.text) &&
        Objects.equals(this.nextSceneId, choice.nextSceneId) &&
        Objects.equals(this.consequence, choice.consequence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, nextSceneId, consequence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Choice {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    nextSceneId: ").append(toIndentedString(nextSceneId)).append("\n");
    sb.append("    consequence: ").append(toIndentedString(consequence)).append("\n");
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

