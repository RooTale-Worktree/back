package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * UserSettingsGet200ResponsePrivacy
 */

@JsonTypeName("_user_settings_get_200_response_privacy")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserSettingsGet200ResponsePrivacy {

  /**
   * �꾨줈�� 怨듦컻 �ㅼ젙
   */
  public enum ProfileVisibilityEnum {
    PUBLIC("public"),
    
    FRIENDS("friends"),
    
    PRIVATE("private");

    private final String value;

    ProfileVisibilityEnum(String value) {
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
    public static ProfileVisibilityEnum fromValue(String value) {
      for (ProfileVisibilityEnum b : ProfileVisibilityEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ProfileVisibilityEnum profileVisibility;

  private @Nullable Boolean storySharing;

  public UserSettingsGet200ResponsePrivacy profileVisibility(@Nullable ProfileVisibilityEnum profileVisibility) {
    this.profileVisibility = profileVisibility;
    return this;
  }

  /**
   * �꾨줈�� 怨듦컻 �ㅼ젙
   * @return profileVisibility
   */
  
  @Schema(name = "profile_visibility", description = "�꾨줈�� 怨듦컻 �ㅼ젙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("profile_visibility")
  public @Nullable ProfileVisibilityEnum getProfileVisibility() {
    return profileVisibility;
  }

  public void setProfileVisibility(@Nullable ProfileVisibilityEnum profileVisibility) {
    this.profileVisibility = profileVisibility;
  }

  public UserSettingsGet200ResponsePrivacy storySharing(@Nullable Boolean storySharing) {
    this.storySharing = storySharing;
    return this;
  }

  /**
   * �ㅽ넗由� 怨듭쑀 �덉슜
   * @return storySharing
   */
  
  @Schema(name = "story_sharing", description = "�ㅽ넗由� 怨듭쑀 �덉슜", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("story_sharing")
  public @Nullable Boolean getStorySharing() {
    return storySharing;
  }

  public void setStorySharing(@Nullable Boolean storySharing) {
    this.storySharing = storySharing;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserSettingsGet200ResponsePrivacy userSettingsGet200ResponsePrivacy = (UserSettingsGet200ResponsePrivacy) o;
    return Objects.equals(this.profileVisibility, userSettingsGet200ResponsePrivacy.profileVisibility) &&
        Objects.equals(this.storySharing, userSettingsGet200ResponsePrivacy.storySharing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(profileVisibility, storySharing);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSettingsGet200ResponsePrivacy {\n");
    sb.append("    profileVisibility: ").append(toIndentedString(profileVisibility)).append("\n");
    sb.append("    storySharing: ").append(toIndentedString(storySharing)).append("\n");
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

