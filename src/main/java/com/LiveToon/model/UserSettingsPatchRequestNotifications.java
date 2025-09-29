package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * UserSettingsPatchRequestNotifications
 */

@JsonTypeName("_user_settings_patch_request_notifications")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserSettingsPatchRequestNotifications {

  private @Nullable Boolean email;

  private @Nullable Boolean push;

  private @Nullable Boolean storyUpdates;

  public UserSettingsPatchRequestNotifications email(@Nullable Boolean email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  
  @Schema(name = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public @Nullable Boolean getEmail() {
    return email;
  }

  public void setEmail(@Nullable Boolean email) {
    this.email = email;
  }

  public UserSettingsPatchRequestNotifications push(@Nullable Boolean push) {
    this.push = push;
    return this;
  }

  /**
   * Get push
   * @return push
   */
  
  @Schema(name = "push", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("push")
  public @Nullable Boolean getPush() {
    return push;
  }

  public void setPush(@Nullable Boolean push) {
    this.push = push;
  }

  public UserSettingsPatchRequestNotifications storyUpdates(@Nullable Boolean storyUpdates) {
    this.storyUpdates = storyUpdates;
    return this;
  }

  /**
   * Get storyUpdates
   * @return storyUpdates
   */
  
  @Schema(name = "story_updates", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    UserSettingsPatchRequestNotifications userSettingsPatchRequestNotifications = (UserSettingsPatchRequestNotifications) o;
    return Objects.equals(this.email, userSettingsPatchRequestNotifications.email) &&
        Objects.equals(this.push, userSettingsPatchRequestNotifications.push) &&
        Objects.equals(this.storyUpdates, userSettingsPatchRequestNotifications.storyUpdates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, push, storyUpdates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSettingsPatchRequestNotifications {\n");
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

