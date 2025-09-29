package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * UserSettingsGet200ResponseNotifications
 */

@JsonTypeName("_user_settings_get_200_response_notifications")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserSettingsGet200ResponseNotifications {

  private @Nullable Boolean email;

  private @Nullable Boolean push;

  private @Nullable Boolean storyUpdates;

  public UserSettingsGet200ResponseNotifications email(@Nullable Boolean email) {
    this.email = email;
    return this;
  }

  /**
   * �대찓�� �뚮┝ �ㅼ젙
   * @return email
   */
  
  @Schema(name = "email", description = "�대찓�� �뚮┝ �ㅼ젙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public @Nullable Boolean getEmail() {
    return email;
  }

  public void setEmail(@Nullable Boolean email) {
    this.email = email;
  }

  public UserSettingsGet200ResponseNotifications push(@Nullable Boolean push) {
    this.push = push;
    return this;
  }

  /**
   * �몄떆 �뚮┝ �ㅼ젙
   * @return push
   */
  
  @Schema(name = "push", description = "�몄떆 �뚮┝ �ㅼ젙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("push")
  public @Nullable Boolean getPush() {
    return push;
  }

  public void setPush(@Nullable Boolean push) {
    this.push = push;
  }

  public UserSettingsGet200ResponseNotifications storyUpdates(@Nullable Boolean storyUpdates) {
    this.storyUpdates = storyUpdates;
    return this;
  }

  /**
   * �ㅽ넗由� �낅뜲�댄듃 �뚮┝
   * @return storyUpdates
   */
  
  @Schema(name = "story_updates", description = "�ㅽ넗由� �낅뜲�댄듃 �뚮┝", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("story_updates")
  public @Nullable Boolean getStoryUpdates() {
    return storyUpdates;
  }

  public void setStoryUpdates(@Nullable Boolean storyUpdates) {
    this.storyUpdates = storyUpdates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserSettingsGet200ResponseNotifications userSettingsGet200ResponseNotifications = (UserSettingsGet200ResponseNotifications) o;
    return Objects.equals(this.email, userSettingsGet200ResponseNotifications.email) &&
        Objects.equals(this.push, userSettingsGet200ResponseNotifications.push) &&
        Objects.equals(this.storyUpdates, userSettingsGet200ResponseNotifications.storyUpdates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, push, storyUpdates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSettingsGet200ResponseNotifications {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    push: ").append(toIndentedString(push)).append("\n");
    sb.append("    storyUpdates: ").append(toIndentedString(storyUpdates)).append("\n");
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

