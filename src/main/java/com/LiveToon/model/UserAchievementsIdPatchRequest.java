package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * UserAchievementsIdPatchRequest
 */

@JsonTypeName("_user_achievements__id__patch_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserAchievementsIdPatchRequest {

  private @Nullable Integer progress;

  private @Nullable Boolean isCompleted;

  public UserAchievementsIdPatchRequest progress(@Nullable Integer progress) {
    this.progress = progress;
    return this;
  }

  /**
   * 吏꾪뻾�� (0-100)
   * minimum: 0
   * maximum: 100
   * @return progress
   */
  @Min(0) @Max(100) 
  @Schema(name = "progress", description = "吏꾪뻾�� (0-100)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("progress")
  public @Nullable Integer getProgress() {
    return progress;
  }

  public void setProgress(@Nullable Integer progress) {
    this.progress = progress;
  }

  public UserAchievementsIdPatchRequest isCompleted(@Nullable Boolean isCompleted) {
    this.isCompleted = isCompleted;
    return this;
  }

  /**
   * �꾨즺 �щ�
   * @return isCompleted
   */
  
  @Schema(name = "is_completed", description = "�꾨즺 �щ�", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("is_completed")
  public @Nullable Boolean getIsCompleted() {
    return isCompleted;
  }

  public void setIsCompleted(@Nullable Boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserAchievementsIdPatchRequest userAchievementsIdPatchRequest = (UserAchievementsIdPatchRequest) o;
    return Objects.equals(this.progress, userAchievementsIdPatchRequest.progress) &&
        Objects.equals(this.isCompleted, userAchievementsIdPatchRequest.isCompleted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(progress, isCompleted);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserAchievementsIdPatchRequest {\n");
    sb.append("    progress: ").append(toIndentedString(progress)).append("\n");
    sb.append("    isCompleted: ").append(toIndentedString(isCompleted)).append("\n");
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

