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
 * UserAchievementsGet200Response
 */

@JsonTypeName("_user_achievements_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserAchievementsGet200Response {

  @Valid
  private List<@Valid Achievement> achievements = new ArrayList<>();

  private @Nullable Integer totalCompleted;

  private @Nullable Integer totalAvailable;

  public UserAchievementsGet200Response achievements(List<@Valid Achievement> achievements) {
    this.achievements = achievements;
    return this;
  }

  public UserAchievementsGet200Response addAchievementsItem(Achievement achievementsItem) {
    if (this.achievements == null) {
      this.achievements = new ArrayList<>();
    }
    this.achievements.add(achievementsItem);
    return this;
  }

  /**
   * Get achievements
   * @return achievements
   */
  @Valid 
  @Schema(name = "achievements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("achievements")
  public List<@Valid Achievement> getAchievements() {
    return achievements;
  }

  public void setAchievements(List<@Valid Achievement> achievements) {
    this.achievements = achievements;
  }

  public UserAchievementsGet200Response totalCompleted(@Nullable Integer totalCompleted) {
    this.totalCompleted = totalCompleted;
    return this;
  }

  /**
   * �꾨즺�� �꾩쟾怨쇱젣 ��
   * @return totalCompleted
   */
  
  @Schema(name = "total_completed", description = "�꾨즺�� �꾩쟾怨쇱젣 ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total_completed")
  public @Nullable Integer getTotalCompleted() {
    return totalCompleted;
  }

  public void setTotalCompleted(@Nullable Integer totalCompleted) {
    this.totalCompleted = totalCompleted;
  }

  public UserAchievementsGet200Response totalAvailable(@Nullable Integer totalAvailable) {
    this.totalAvailable = totalAvailable;
    return this;
  }

  /**
   * �꾩껜 �꾩쟾怨쇱젣 ��
   * @return totalAvailable
   */
  
  @Schema(name = "total_available", description = "�꾩껜 �꾩쟾怨쇱젣 ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total_available")
  public @Nullable Integer getTotalAvailable() {
    return totalAvailable;
  }

  public void setTotalAvailable(@Nullable Integer totalAvailable) {
    this.totalAvailable = totalAvailable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserAchievementsGet200Response userAchievementsGet200Response = (UserAchievementsGet200Response) o;
    return Objects.equals(this.achievements, userAchievementsGet200Response.achievements) &&
        Objects.equals(this.totalCompleted, userAchievementsGet200Response.totalCompleted) &&
        Objects.equals(this.totalAvailable, userAchievementsGet200Response.totalAvailable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(achievements, totalCompleted, totalAvailable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserAchievementsGet200Response {\n");
    sb.append("    achievements: ").append(toIndentedString(achievements)).append("\n");
    sb.append("    totalCompleted: ").append(toIndentedString(totalCompleted)).append("\n");
    sb.append("    totalAvailable: ").append(toIndentedString(totalAvailable)).append("\n");
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

