package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageProfileGet200ResponseStats
 */

@JsonTypeName("_mypage_profile_get_200_response_stats")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageProfileGet200ResponseStats {

  private @Nullable Integer storiesCompleted;

  private @Nullable Integer charactersCreated;

  private @Nullable Integer totalChatMessages;

  private @Nullable Integer daysActive;

  public MypageProfileGet200ResponseStats storiesCompleted(@Nullable Integer storiesCompleted) {
    this.storiesCompleted = storiesCompleted;
    return this;
  }

  /**
   * �꾨즺�� �ㅽ넗由� ��
   * @return storiesCompleted
   */
  
  @Schema(name = "stories_completed", description = "�꾨즺�� �ㅽ넗由� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stories_completed")
  public @Nullable Integer getStoriesCompleted() {
    return storiesCompleted;
  }

  public void setStoriesCompleted(@Nullable Integer storiesCompleted) {
    this.storiesCompleted = storiesCompleted;
  }

  public MypageProfileGet200ResponseStats charactersCreated(@Nullable Integer charactersCreated) {
    this.charactersCreated = charactersCreated;
    return this;
  }

  /**
   * �앹꽦�� 罹먮┃�� ��
   * @return charactersCreated
   */
  
  @Schema(name = "characters_created", description = "�앹꽦�� 罹먮┃�� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("characters_created")
  public @Nullable Integer getCharactersCreated() {
    return charactersCreated;
  }

  public void setCharactersCreated(@Nullable Integer charactersCreated) {
    this.charactersCreated = charactersCreated;
  }

  public MypageProfileGet200ResponseStats totalChatMessages(@Nullable Integer totalChatMessages) {
    this.totalChatMessages = totalChatMessages;
    return this;
  }

  /**
   * 珥� 梨꾪똿 硫붿떆吏� ��
   * @return totalChatMessages
   */
  
  @Schema(name = "total_chat_messages", description = "珥� 梨꾪똿 硫붿떆吏� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total_chat_messages")
  public @Nullable Integer getTotalChatMessages() {
    return totalChatMessages;
  }

  public void setTotalChatMessages(@Nullable Integer totalChatMessages) {
    this.totalChatMessages = totalChatMessages;
  }

  public MypageProfileGet200ResponseStats daysActive(@Nullable Integer daysActive) {
    this.daysActive = daysActive;
    return this;
  }

  /**
   * �쒖꽦 �ъ슜 �쇱닔
   * @return daysActive
   */
  
  @Schema(name = "days_active", description = "�쒖꽦 �ъ슜 �쇱닔", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("days_active")
  public @Nullable Integer getDaysActive() {
    return daysActive;
  }

  public void setDaysActive(@Nullable Integer daysActive) {
    this.daysActive = daysActive;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageProfileGet200ResponseStats mypageProfileGet200ResponseStats = (MypageProfileGet200ResponseStats) o;
    return Objects.equals(this.storiesCompleted, mypageProfileGet200ResponseStats.storiesCompleted) &&
        Objects.equals(this.charactersCreated, mypageProfileGet200ResponseStats.charactersCreated) &&
        Objects.equals(this.totalChatMessages, mypageProfileGet200ResponseStats.totalChatMessages) &&
        Objects.equals(this.daysActive, mypageProfileGet200ResponseStats.daysActive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storiesCompleted, charactersCreated, totalChatMessages, daysActive);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageProfileGet200ResponseStats {\n");
    sb.append("    storiesCompleted: ").append(toIndentedString(storiesCompleted)).append("\n");
    sb.append("    charactersCreated: ").append(toIndentedString(charactersCreated)).append("\n");
    sb.append("    totalChatMessages: ").append(toIndentedString(totalChatMessages)).append("\n");
    sb.append("    daysActive: ").append(toIndentedString(daysActive)).append("\n");
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

