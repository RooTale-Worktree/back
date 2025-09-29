package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageProfileGet200Response
 */

@JsonTypeName("_mypage_profile_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageProfileGet200Response {

  private @Nullable User user;

  private @Nullable MypageProfileGet200ResponseStats stats;

  private @Nullable MypageProfileGet200ResponseAchievementsSummary achievementsSummary;

  public MypageProfileGet200Response user(@Nullable User user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
   */
  @Valid 
  @Schema(name = "user", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("user")
  public @Nullable User getUser() {
    return user;
  }

  public void setUser(@Nullable User user) {
    this.user = user;
  }

  public MypageProfileGet200Response stats(@Nullable MypageProfileGet200ResponseStats stats) {
    this.stats = stats;
    return this;
  }

  /**
   * Get stats
   * @return stats
   */
  @Valid 
  @Schema(name = "stats", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stats")
  public @Nullable MypageProfileGet200ResponseStats getStats() {
    return stats;
  }

  public void setStats(@Nullable MypageProfileGet200ResponseStats stats) {
    this.stats = stats;
  }

  public MypageProfileGet200Response achievementsSummary(@Nullable MypageProfileGet200ResponseAchievementsSummary achievementsSummary) {
    this.achievementsSummary = achievementsSummary;
    return this;
  }

  /**
   * Get achievementsSummary
   * @return achievementsSummary
   */
  @Valid 
  @Schema(name = "achievements_summary", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("achievements_summary")
  public @Nullable MypageProfileGet200ResponseAchievementsSummary getAchievementsSummary() {
    return achievementsSummary;
  }

  public void setAchievementsSummary(@Nullable MypageProfileGet200ResponseAchievementsSummary achievementsSummary) {
    this.achievementsSummary = achievementsSummary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageProfileGet200Response mypageProfileGet200Response = (MypageProfileGet200Response) o;
    return Objects.equals(this.user, mypageProfileGet200Response.user) &&
        Objects.equals(this.stats, mypageProfileGet200Response.stats) &&
        Objects.equals(this.achievementsSummary, mypageProfileGet200Response.achievementsSummary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, stats, achievementsSummary);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageProfileGet200Response {\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    stats: ").append(toIndentedString(stats)).append("\n");
    sb.append("    achievementsSummary: ").append(toIndentedString(achievementsSummary)).append("\n");
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

