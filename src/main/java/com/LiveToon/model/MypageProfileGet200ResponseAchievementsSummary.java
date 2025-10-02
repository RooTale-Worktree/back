package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
//import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageProfileGet200ResponseAchievementsSummary
 */

@JsonTypeName("_mypage_profile_get_200_response_achievements_summary")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageProfileGet200ResponseAchievementsSummary {

  private @Nullable Integer totalAchievements;

  private @Nullable Integer completedAchievements;

  @Valid
  private List<@Valid Achievement> recentAchievements = new ArrayList<>();

  public MypageProfileGet200ResponseAchievementsSummary totalAchievements(@Nullable Integer totalAchievements) {
    this.totalAchievements = totalAchievements;
    return this;
  }

  /**
   * Get totalAchievements
   * @return totalAchievements
   */
  
  @Schema(name = "total_achievements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total_achievements")
  public @Nullable Integer getTotalAchievements() {
    return totalAchievements;
  }

  public void setTotalAchievements(@Nullable Integer totalAchievements) {
    this.totalAchievements = totalAchievements;
  }

  public MypageProfileGet200ResponseAchievementsSummary completedAchievements(@Nullable Integer completedAchievements) {
    this.completedAchievements = completedAchievements;
    return this;
  }

  /**
   * Get completedAchievements
   * @return completedAchievements
   */
  
  @Schema(name = "completed_achievements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("completed_achievements")
  public @Nullable Integer getCompletedAchievements() {
    return completedAchievements;
  }

  public void setCompletedAchievements(@Nullable Integer completedAchievements) {
    this.completedAchievements = completedAchievements;
  }

  public MypageProfileGet200ResponseAchievementsSummary recentAchievements(List<@Valid Achievement> recentAchievements) {
    this.recentAchievements = recentAchievements;
    return this;
  }

  public MypageProfileGet200ResponseAchievementsSummary addRecentAchievementsItem(Achievement recentAchievementsItem) {
    if (this.recentAchievements == null) {
      this.recentAchievements = new ArrayList<>();
    }
    this.recentAchievements.add(recentAchievementsItem);
    return this;
  }

  /**
   * Get recentAchievements
   * @return recentAchievements
   */
  @Valid 
  @Schema(name = "recent_achievements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recent_achievements")
  public List<@Valid Achievement> getRecentAchievements() {
    return recentAchievements;
  }

  public void setRecentAchievements(List<@Valid Achievement> recentAchievements) {
    this.recentAchievements = recentAchievements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageProfileGet200ResponseAchievementsSummary mypageProfileGet200ResponseAchievementsSummary = (MypageProfileGet200ResponseAchievementsSummary) o;
    return Objects.equals(this.totalAchievements, mypageProfileGet200ResponseAchievementsSummary.totalAchievements) &&
        Objects.equals(this.completedAchievements, mypageProfileGet200ResponseAchievementsSummary.completedAchievements) &&
        Objects.equals(this.recentAchievements, mypageProfileGet200ResponseAchievementsSummary.recentAchievements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalAchievements, completedAchievements, recentAchievements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageProfileGet200ResponseAchievementsSummary {\n");
    sb.append("    totalAchievements: ").append(toIndentedString(totalAchievements)).append("\n");
    sb.append("    completedAchievements: ").append(toIndentedString(completedAchievements)).append("\n");
    sb.append("    recentAchievements: ").append(toIndentedString(recentAchievements)).append("\n");
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

