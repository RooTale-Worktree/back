package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * StoriesStoryIdProgressPostRequest
 */

@JsonTypeName("_stories__story_id__progress_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class StoriesStoryIdProgressPostRequest {

  private UUID choiceId;

  private @Nullable String customInput;

  public StoriesStoryIdProgressPostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public StoriesStoryIdProgressPostRequest(UUID choiceId) {
    this.choiceId = choiceId;
  }

  public StoriesStoryIdProgressPostRequest choiceId(UUID choiceId) {
    this.choiceId = choiceId;
    return this;
  }

  /**
   * �좏깮�� �좏깮吏��� ID
   * @return choiceId
   */
  @NotNull @Valid 
  @Schema(name = "choice_id", description = "�좏깮�� �좏깮吏��� ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("choice_id")
  public UUID getChoiceId() {
    return choiceId;
  }

  public void setChoiceId(UUID choiceId) {
    this.choiceId = choiceId;
  }

  public StoriesStoryIdProgressPostRequest customInput(@Nullable String customInput) {
    this.customInput = customInput;
    return this;
  }

  /**
   * �ъ슜�� �뺤쓽 �낅젰 (�좏깮�ы빆)
   * @return customInput
   */
  @Size(max = 500) 
  @Schema(name = "custom_input", description = "�ъ슜�� �뺤쓽 �낅젰 (�좏깮�ы빆)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("custom_input")
  public @Nullable String getCustomInput() {
    return customInput;
  }

  public void setCustomInput(@Nullable String customInput) {
    this.customInput = customInput;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoriesStoryIdProgressPostRequest storiesStoryIdProgressPostRequest = (StoriesStoryIdProgressPostRequest) o;
    return Objects.equals(this.choiceId, storiesStoryIdProgressPostRequest.choiceId) &&
        Objects.equals(this.customInput, storiesStoryIdProgressPostRequest.customInput);
  }

  @Override
  public int hashCode() {
    return Objects.hash(choiceId, customInput);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoriesStoryIdProgressPostRequest {\n");
    sb.append("    choiceId: ").append(toIndentedString(choiceId)).append("\n");
    sb.append("    customInput: ").append(toIndentedString(customInput)).append("\n");
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

