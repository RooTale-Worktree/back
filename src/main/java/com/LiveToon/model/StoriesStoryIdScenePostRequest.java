package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * StoriesStoryIdScenePostRequest
 */

@JsonTypeName("_stories__story_id__scene_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class StoriesStoryIdScenePostRequest {

  private String userInput;

  private @Nullable String sceneContext;

  /**
   * �대�吏� �ㅽ��� (�좏깮�ы빆)
   */
  public enum ImageStyleEnum {
    REALISTIC("realistic"),
    
    ANIME("anime"),
    
    CARTOON("cartoon"),
    
    WATERCOLOR("watercolor"),
    
    OIL_PAINTING("oil_painting");

    private final String value;

    ImageStyleEnum(String value) {
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
    public static ImageStyleEnum fromValue(String value) {
      for (ImageStyleEnum b : ImageStyleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ImageStyleEnum imageStyle;

  public StoriesStoryIdScenePostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public StoriesStoryIdScenePostRequest(String userInput) {
    this.userInput = userInput;
  }

  public StoriesStoryIdScenePostRequest userInput(String userInput) {
    this.userInput = userInput;
    return this;
  }

  /**
   * �ъ슜�� �낅젰 �띿뒪��
   * @return userInput
   */
  @NotNull @Size(min = 1, max = 1000) 
  @Schema(name = "user_input", description = "�ъ슜�� �낅젰 �띿뒪��", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("user_input")
  public String getUserInput() {
    return userInput;
  }

  public void setUserInput(String userInput) {
    this.userInput = userInput;
  }

  public StoriesStoryIdScenePostRequest sceneContext(@Nullable String sceneContext) {
    this.sceneContext = sceneContext;
    return this;
  }

  /**
   * �λ㈃ 而⑦뀓�ㅽ듃 (�좏깮�ы빆)
   * @return sceneContext
   */
  @Size(max = 2000) 
  @Schema(name = "scene_context", description = "�λ㈃ 而⑦뀓�ㅽ듃 (�좏깮�ы빆)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("scene_context")
  public @Nullable String getSceneContext() {
    return sceneContext;
  }

  public void setSceneContext(@Nullable String sceneContext) {
    this.sceneContext = sceneContext;
  }

  public StoriesStoryIdScenePostRequest imageStyle(@Nullable ImageStyleEnum imageStyle) {
    this.imageStyle = imageStyle;
    return this;
  }

  /**
   * �대�吏� �ㅽ��� (�좏깮�ы빆)
   * @return imageStyle
   */
  
  @Schema(name = "image_style", description = "�대�吏� �ㅽ��� (�좏깮�ы빆)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image_style")
  public @Nullable ImageStyleEnum getImageStyle() {
    return imageStyle;
  }

  public void setImageStyle(@Nullable ImageStyleEnum imageStyle) {
    this.imageStyle = imageStyle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoriesStoryIdScenePostRequest storiesStoryIdScenePostRequest = (StoriesStoryIdScenePostRequest) o;
    return Objects.equals(this.userInput, storiesStoryIdScenePostRequest.userInput) &&
        Objects.equals(this.sceneContext, storiesStoryIdScenePostRequest.sceneContext) &&
        Objects.equals(this.imageStyle, storiesStoryIdScenePostRequest.imageStyle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userInput, sceneContext, imageStyle);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoriesStoryIdScenePostRequest {\n");
    sb.append("    userInput: ").append(toIndentedString(userInput)).append("\n");
    sb.append("    sceneContext: ").append(toIndentedString(sceneContext)).append("\n");
    sb.append("    imageStyle: ").append(toIndentedString(imageStyle)).append("\n");
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

