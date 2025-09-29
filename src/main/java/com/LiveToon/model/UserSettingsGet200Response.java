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
 * UserSettingsGet200Response
 */

@JsonTypeName("_user_settings_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserSettingsGet200Response {

  /**
   * �뚮쭏 �ㅼ젙
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
   * �몄뼱 �ㅼ젙
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

  private @Nullable UserSettingsGet200ResponseNotifications notifications;

  private @Nullable UserSettingsGet200ResponsePrivacy privacy;

  private @Nullable UserSettingsGet200ResponseStoryPreferences storyPreferences;

  public UserSettingsGet200Response theme(@Nullable ThemeEnum theme) {
    this.theme = theme;
    return this;
  }

  /**
   * �뚮쭏 �ㅼ젙
   * @return theme
   */
  
  @Schema(name = "theme", description = "�뚮쭏 �ㅼ젙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("theme")
  public @Nullable ThemeEnum getTheme() {
    return theme;
  }

  public void setTheme(@Nullable ThemeEnum theme) {
    this.theme = theme;
  }

  public UserSettingsGet200Response language(@Nullable LanguageEnum language) {
    this.language = language;
    return this;
  }

  /**
   * �몄뼱 �ㅼ젙
   * @return language
   */
  
  @Schema(name = "language", description = "�몄뼱 �ㅼ젙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("language")
  public @Nullable LanguageEnum getLanguage() {
    return language;
  }

  public void setLanguage(@Nullable LanguageEnum language) {
    this.language = language;
  }

  public UserSettingsGet200Response notifications(@Nullable UserSettingsGet200ResponseNotifications notifications) {
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
  public @Nullable UserSettingsGet200ResponseNotifications getNotifications() {
    return notifications;
  }

  public void setNotifications(@Nullable UserSettingsGet200ResponseNotifications notifications) {
    this.notifications = notifications;
  }

  public UserSettingsGet200Response privacy(@Nullable UserSettingsGet200ResponsePrivacy privacy) {
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
  public @Nullable UserSettingsGet200ResponsePrivacy getPrivacy() {
    return privacy;
  }

  public void setPrivacy(@Nullable UserSettingsGet200ResponsePrivacy privacy) {
    this.privacy = privacy;
  }

  public UserSettingsGet200Response storyPreferences(@Nullable UserSettingsGet200ResponseStoryPreferences storyPreferences) {
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
  public @Nullable UserSettingsGet200ResponseStoryPreferences getStoryPreferences() {
    return storyPreferences;
  }

  public void setStoryPreferences(@Nullable UserSettingsGet200ResponseStoryPreferences storyPreferences) {
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
    UserSettingsGet200Response userSettingsGet200Response = (UserSettingsGet200Response) o;
    return Objects.equals(this.theme, userSettingsGet200Response.theme) &&
        Objects.equals(this.language, userSettingsGet200Response.language) &&
        Objects.equals(this.notifications, userSettingsGet200Response.notifications) &&
        Objects.equals(this.privacy, userSettingsGet200Response.privacy) &&
        Objects.equals(this.storyPreferences, userSettingsGet200Response.storyPreferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(theme, language, notifications, privacy, storyPreferences);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSettingsGet200Response {\n");
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

