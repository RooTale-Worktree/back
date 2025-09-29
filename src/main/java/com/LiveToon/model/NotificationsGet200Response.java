package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * NotificationsGet200Response
 */

@JsonTypeName("_notifications_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class NotificationsGet200Response {

  @Valid
  private List<@Valid Notification> notifications = new ArrayList<>();

  private @Nullable Integer unreadCount;

  private @Nullable WorldsGet200ResponsePagination pagination;

  public NotificationsGet200Response notifications(List<@Valid Notification> notifications) {
    this.notifications = notifications;
    return this;
  }

  public NotificationsGet200Response addNotificationsItem(Notification notificationsItem) {
    if (this.notifications == null) {
      this.notifications = new ArrayList<>();
    }
    this.notifications.add(notificationsItem);
    return this;
  }

  /**
   * Get notifications
   * @return notifications
   */
  @Valid 
  @Schema(name = "notifications", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("notifications")
  public List<@Valid Notification> getNotifications() {
    return notifications;
  }

  public void setNotifications(List<@Valid Notification> notifications) {
    this.notifications = notifications;
  }

  public NotificationsGet200Response unreadCount(@Nullable Integer unreadCount) {
    this.unreadCount = unreadCount;
    return this;
  }

  /**
   * �쎌� �딆� �뚮┝ ��
   * @return unreadCount
   */
  
  @Schema(name = "unread_count", description = "�쎌� �딆� �뚮┝ ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unread_count")
  public @Nullable Integer getUnreadCount() {
    return unreadCount;
  }

  public void setUnreadCount(@Nullable Integer unreadCount) {
    this.unreadCount = unreadCount;
  }

  public NotificationsGet200Response pagination(@Nullable WorldsGet200ResponsePagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   * @return pagination
   */
  @Valid 
  @Schema(name = "pagination", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pagination")
  public @Nullable WorldsGet200ResponsePagination getPagination() {
    return pagination;
  }

  public void setPagination(@Nullable WorldsGet200ResponsePagination pagination) {
    this.pagination = pagination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotificationsGet200Response notificationsGet200Response = (NotificationsGet200Response) o;
    return Objects.equals(this.notifications, notificationsGet200Response.notifications) &&
        Objects.equals(this.unreadCount, notificationsGet200Response.unreadCount) &&
        Objects.equals(this.pagination, notificationsGet200Response.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(notifications, unreadCount, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificationsGet200Response {\n");
    sb.append("    notifications: ").append(toIndentedString(notifications)).append("\n");
    sb.append("    unreadCount: ").append(toIndentedString(unreadCount)).append("\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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

