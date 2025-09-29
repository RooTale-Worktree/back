package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ChatCharacterIdSettingsPatch200Response
 */

@JsonTypeName("_chat__character_id__settings_patch_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class ChatCharacterIdSettingsPatch200Response {

  private @Nullable String message;

  private @Nullable Object settings;

  public ChatCharacterIdSettingsPatch200Response message(@Nullable String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   */
  
  @Schema(name = "message", example = "梨꾪똿 �ㅼ젙�� �깃났�곸쑝濡� �낅뜲�댄듃�섏뿀�듬땲��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public @Nullable String getMessage() {
    return message;
  }

  public void setMessage(@Nullable String message) {
    this.message = message;
  }

  public ChatCharacterIdSettingsPatch200Response settings(@Nullable Object settings) {
    this.settings = settings;
    return this;
  }

  /**
   * �낅뜲�댄듃�� �ㅼ젙 �뺣낫
   * @return settings
   */
  
  @Schema(name = "settings", description = "�낅뜲�댄듃�� �ㅼ젙 �뺣낫", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("settings")
  public @Nullable Object getSettings() {
    return settings;
  }

  public void setSettings(@Nullable Object settings) {
    this.settings = settings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatCharacterIdSettingsPatch200Response chatCharacterIdSettingsPatch200Response = (ChatCharacterIdSettingsPatch200Response) o;
    return Objects.equals(this.message, chatCharacterIdSettingsPatch200Response.message) &&
        Objects.equals(this.settings, chatCharacterIdSettingsPatch200Response.settings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, settings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatCharacterIdSettingsPatch200Response {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    settings: ").append(toIndentedString(settings)).append("\n");
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

