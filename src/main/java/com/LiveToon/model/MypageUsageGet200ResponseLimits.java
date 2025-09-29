package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageUsageGet200ResponseLimits
 */

@JsonTypeName("_mypage_usage_get_200_response_limits")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageUsageGet200ResponseLimits {

  private @Nullable Integer maxStories;

  private @Nullable Integer maxCharacters;

  private @Nullable Integer monthlyChatMessages;

  private @Nullable Integer monthlyImages;

  public MypageUsageGet200ResponseLimits maxStories(@Nullable Integer maxStories) {
    this.maxStories = maxStories;
    return this;
  }

  /**
   * 理쒕� �ㅽ넗由� ��
   * @return maxStories
   */
  
  @Schema(name = "max_stories", description = "理쒕� �ㅽ넗由� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_stories")
  public @Nullable Integer getMaxStories() {
    return maxStories;
  }

  public void setMaxStories(@Nullable Integer maxStories) {
    this.maxStories = maxStories;
  }

  public MypageUsageGet200ResponseLimits maxCharacters(@Nullable Integer maxCharacters) {
    this.maxCharacters = maxCharacters;
    return this;
  }

  /**
   * 理쒕� 罹먮┃�� ��
   * @return maxCharacters
   */
  
  @Schema(name = "max_characters", description = "理쒕� 罹먮┃�� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_characters")
  public @Nullable Integer getMaxCharacters() {
    return maxCharacters;
  }

  public void setMaxCharacters(@Nullable Integer maxCharacters) {
    this.maxCharacters = maxCharacters;
  }

  public MypageUsageGet200ResponseLimits monthlyChatMessages(@Nullable Integer monthlyChatMessages) {
    this.monthlyChatMessages = monthlyChatMessages;
    return this;
  }

  /**
   * �붽컙 梨꾪똿 硫붿떆吏� �쒕룄
   * @return monthlyChatMessages
   */
  
  @Schema(name = "monthly_chat_messages", description = "�붽컙 梨꾪똿 硫붿떆吏� �쒕룄", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("monthly_chat_messages")
  public @Nullable Integer getMonthlyChatMessages() {
    return monthlyChatMessages;
  }

  public void setMonthlyChatMessages(@Nullable Integer monthlyChatMessages) {
    this.monthlyChatMessages = monthlyChatMessages;
  }

  public MypageUsageGet200ResponseLimits monthlyImages(@Nullable Integer monthlyImages) {
    this.monthlyImages = monthlyImages;
    return this;
  }

  /**
   * �붽컙 �대�吏� �앹꽦 �쒕룄
   * @return monthlyImages
   */
  
  @Schema(name = "monthly_images", description = "�붽컙 �대�吏� �앹꽦 �쒕룄", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("monthly_images")
  public @Nullable Integer getMonthlyImages() {
    return monthlyImages;
  }

  public void setMonthlyImages(@Nullable Integer monthlyImages) {
    this.monthlyImages = monthlyImages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageUsageGet200ResponseLimits mypageUsageGet200ResponseLimits = (MypageUsageGet200ResponseLimits) o;
    return Objects.equals(this.maxStories, mypageUsageGet200ResponseLimits.maxStories) &&
        Objects.equals(this.maxCharacters, mypageUsageGet200ResponseLimits.maxCharacters) &&
        Objects.equals(this.monthlyChatMessages, mypageUsageGet200ResponseLimits.monthlyChatMessages) &&
        Objects.equals(this.monthlyImages, mypageUsageGet200ResponseLimits.monthlyImages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxStories, maxCharacters, monthlyChatMessages, monthlyImages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageUsageGet200ResponseLimits {\n");
    sb.append("    maxStories: ").append(toIndentedString(maxStories)).append("\n");
    sb.append("    maxCharacters: ").append(toIndentedString(maxCharacters)).append("\n");
    sb.append("    monthlyChatMessages: ").append(toIndentedString(monthlyChatMessages)).append("\n");
    sb.append("    monthlyImages: ").append(toIndentedString(monthlyImages)).append("\n");
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

