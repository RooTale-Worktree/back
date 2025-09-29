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
 * UserSettingsGet200ResponseStoryPreferences
 */

@JsonTypeName("_user_settings_get_200_response_story_preferences")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class UserSettingsGet200ResponseStoryPreferences {

  private @Nullable Boolean autoSave;

  /**
   * �대�吏� �덉쭏 �ㅼ젙
   */
  public enum ImageQualityEnum {
    LOW("low"),
    
    MEDIUM("medium"),
    
    HIGH("high");

    private final String value;

    ImageQualityEnum(String value) {
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
    public static ImageQualityEnum fromValue(String value) {
      for (ImageQualityEnum b : ImageQualityEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ImageQualityEnum imageQuality;

  public UserSettingsGet200ResponseStoryPreferences autoSave(@Nullable Boolean autoSave) {
    this.autoSave = autoSave;
    return this;
  }

  /**
   * �먮룞 ���� �ㅼ젙
   * @return autoSave
   */
  
  @Schema(name = "auto_save", description = "�먮룞 ���� �ㅼ젙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("auto_save")
  public @Nullable Boolean getAutoSave() {
    return autoSave;
  }

  public void setAutoSave(@Nullable Boolean autoSave) {
    this.autoSave = autoSave;
  }

  public UserSettingsGet200ResponseStoryPreferences imageQuality(@Nullable ImageQualityEnum imageQuality) {
    this.imageQuality = imageQuality;
    return this;
  }

  /**
   * �대�吏� �덉쭏 �ㅼ젙
   * @return imageQuality
   */
  
  @Schema(name = "image_quality", description = "�대�吏� �덉쭏 �ㅼ젙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image_quality")
  public @Nullable ImageQualityEnum getImageQuality() {
    return imageQuality;
  }

  public void setImageQuality(@Nullable ImageQualityEnum imageQuality) {
    this.imageQuality = imageQuality;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserSettingsGet200ResponseStoryPreferences userSettingsGet200ResponseStoryPreferences = (UserSettingsGet200ResponseStoryPreferences) o;
    return Objects.equals(this.autoSave, userSettingsGet200ResponseStoryPreferences.autoSave) &&
        Objects.equals(this.imageQuality, userSettingsGet200ResponseStoryPreferences.imageQuality);
  }

  @Override
  public int hashCode() {
    return Objects.hash(autoSave, imageQuality);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSettingsGet200ResponseStoryPreferences {\n");
    sb.append("    autoSave: ").append(toIndentedString(autoSave)).append("\n");
    sb.append("    imageQuality: ").append(toIndentedString(imageQuality)).append("\n");
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

