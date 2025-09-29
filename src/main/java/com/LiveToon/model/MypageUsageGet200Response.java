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
 * MypageUsageGet200Response
 */

@JsonTypeName("_mypage_usage_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageUsageGet200Response {

  private @Nullable MypageUsageGet200ResponseSubscription subscription;

  private @Nullable MypageUsageGet200ResponseUsage usage;

  private @Nullable MypageUsageGet200ResponseLimits limits;

  public MypageUsageGet200Response subscription(@Nullable MypageUsageGet200ResponseSubscription subscription) {
    this.subscription = subscription;
    return this;
  }

  /**
   * Get subscription
   * @return subscription
   */
  @Valid 
  @Schema(name = "subscription", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subscription")
  public @Nullable MypageUsageGet200ResponseSubscription getSubscription() {
    return subscription;
  }

  public void setSubscription(@Nullable MypageUsageGet200ResponseSubscription subscription) {
    this.subscription = subscription;
  }

  public MypageUsageGet200Response usage(@Nullable MypageUsageGet200ResponseUsage usage) {
    this.usage = usage;
    return this;
  }

  /**
   * Get usage
   * @return usage
   */
  @Valid 
  @Schema(name = "usage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("usage")
  public @Nullable MypageUsageGet200ResponseUsage getUsage() {
    return usage;
  }

  public void setUsage(@Nullable MypageUsageGet200ResponseUsage usage) {
    this.usage = usage;
  }

  public MypageUsageGet200Response limits(@Nullable MypageUsageGet200ResponseLimits limits) {
    this.limits = limits;
    return this;
  }

  /**
   * Get limits
   * @return limits
   */
  @Valid 
  @Schema(name = "limits", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("limits")
  public @Nullable MypageUsageGet200ResponseLimits getLimits() {
    return limits;
  }

  public void setLimits(@Nullable MypageUsageGet200ResponseLimits limits) {
    this.limits = limits;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageUsageGet200Response mypageUsageGet200Response = (MypageUsageGet200Response) o;
    return Objects.equals(this.subscription, mypageUsageGet200Response.subscription) &&
        Objects.equals(this.usage, mypageUsageGet200Response.usage) &&
        Objects.equals(this.limits, mypageUsageGet200Response.limits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscription, usage, limits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageUsageGet200Response {\n");
    sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
    sb.append("    limits: ").append(toIndentedString(limits)).append("\n");
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

