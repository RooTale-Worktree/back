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
 * NotificationsIdReadPatch200Response
 */

@JsonTypeName("_notifications__id__read_patch_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class NotificationsIdReadPatch200Response {

  private @Nullable String message;

  private @Nullable Notification notification;

  public NotificationsIdReadPatch200Response message(@Nullable String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   */
  
  @Schema(name = "message", example = "�뚮┝�� �쎌쓬 泥섎━�섏뿀�듬땲��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public @Nullable String getMessage() {
    return message;
  }

  public void setMessage(@Nullable String message) {
    this.message = message;
  }

  public NotificationsIdReadPatch200Response notification(@Nullable Notification notification) {
    this.notification = notification;
    return this;
  }

  /**
   * Get notification
   * @return notification
   */
  @Valid 
  @Schema(name = "notification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("notification")
  public @Nullable Notification getNotification() {
    return notification;
  }

  public void setNotification(@Nullable Notification notification) {
    this.notification = notification;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotificationsIdReadPatch200Response notificationsIdReadPatch200Response = (NotificationsIdReadPatch200Response) o;
    return Objects.equals(this.message, notificationsIdReadPatch200Response.message) &&
        Objects.equals(this.notification, notificationsIdReadPatch200Response.notification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, notification);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificationsIdReadPatch200Response {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    notification: ").append(toIndentedString(notification)).append("\n");
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

