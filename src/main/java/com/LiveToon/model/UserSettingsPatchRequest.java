package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * UserSettingsPatchRequest
 */

@JsonTypeName("_user_settings_patch_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserSettingsPatchRequest {

  /**
   * Gets or Sets theme
   */
  public enum ThemeEnum {
    LIGHT("light"),
    
    DARK("dark"),
    
    AUTO("auto");

    private final String value;

    ThemeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ThemeEnum fromValue(String value) {
      for (ThemeEnum b : ThemeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ThemeEnum theme;

  /**
   * Gets or Sets language
   */
  public enum LanguageEnum {
    KO("ko"),
    
    EN("en"),
    
    JA("ja"),
    
    ZH("zh");

    private final String value;

    LanguageEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static LanguageEnum fromValue(String value) {
      for (LanguageEnum b : LanguageEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable LanguageEnum language;

  private @Nullable UserSettingsPatchRequestNotifications notifications;

  private @Nullable UserSettingsPatchRequestPrivacy privacy;

  private @Nullable UserSettingsPatchRequestStoryPreferences storyPreferences;

  public UserSettingsPatchRequest theme(@Nullable ThemeEnum theme) {
    this.theme = theme;
    return this;
  }

  /**
   * Get theme
   * @return theme
   */
  
  @Schema(name = "theme", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("theme")
  public @Nullable ThemeEnum getTheme() {
    return theme;
  }

  public void setTheme(@Nullable ThemeEnum theme) {
    this.theme = theme;
  }

  public UserSettingsPatchRequest language(@Nullable LanguageEnum language) {
    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
   */
  
  @Schema(name = "language", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("language")
  public @Nullable LanguageEnum getLanguage() {
    return language;
  }

  public void setLanguage(@Nullable LanguageEnum language) {
    this.language = language;
  }

  public UserSettingsPatchRequest notifications(@Nullable UserSettingsPatchRequestNotifications notifications) {
    this.notifications = notifications;
    return this;
  }

  /**
   * Get notifications
   * @return notifications
   */
  @Valid 
  @Schema(name = "notifications", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("notifications")
  public @Nullable UserSettingsPatchRequestNotifications getNotifications() {
    return notifications;
  }

  public void setNotifications(@Nullable UserSettingsPatchRequestNotifications notifications) {
    this.notifications = notifications;
  }

  public UserSettingsPatchRequest privacy(@Nullable UserSettingsPatchRequestPrivacy privacy) {
    this.privacy = privacy;
    return this;
  }

  /**
   * Get privacy
   * @return privacy
   */
  @Valid 
  @Schema(name = "privacy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("privacy")
  public @Nullable UserSettingsPatchRequestPrivacy getPrivacy() {
    return privacy;
  }

  public void setPrivacy(@Nullable UserSettingsPatchRequestPrivacy privacy) {
    this.privacy = privacy;
  }

  public UserSettingsPatchRequest storyPreferences(@Nullable UserSettingsPatchRequestStoryPreferences storyPreferences) {
    this.storyPreferences = storyPreferences;
    return this;
  }

  /**
   * Get storyPreferences
   * @return storyPreferences
   */
  @Valid 
  @Schema(name = "story_preferences", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("story_preferences")
  public @Nullable UserSettingsPatchRequestStoryPreferences getStoryPreferences() {
    return storyPreferences;
  }

  public void setStoryPreferences(@Nullable UserSettingsPatchRequestStoryPreferences storyPreferences) {
    this.storyPreferences = storyPreferences;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserSettingsPatchRequest userSettingsPatchRequest = (UserSettingsPatchRequest) o;
    return Objects.equals(this.theme, userSettingsPatchRequest.theme) &&
        Objects.equals(this.language, userSettingsPatchRequest.language) &&
        Objects.equals(this.notifications, userSettingsPatchRequest.notifications) &&
        Objects.equals(this.privacy, userSettingsPatchRequest.privacy) &&
        Objects.equals(this.storyPreferences, userSettingsPatchRequest.storyPreferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(theme, language, notifications, privacy, storyPreferences);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSettingsPatchRequest {\n");
    sb.append("    theme: ").append(toIndentedString(theme)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    notifications: ").append(toIndentedString(notifications)).append("\n");
    sb.append("    privacy: ").append(toIndentedString(privacy)).append("\n");
    sb.append("    storyPreferences: ").append(toIndentedString(storyPreferences)).append("\n");
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

