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
 * ChatCharacterIdHistoryGet200Response
 */

@JsonTypeName("_chat__character_id__history_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class ChatCharacterIdHistoryGet200Response {

  @Valid
  private List<@Valid ChatMessage> messages = new ArrayList<>();

  private @Nullable WorldsGet200ResponsePagination pagination;

  public ChatCharacterIdHistoryGet200Response messages(List<@Valid ChatMessage> messages) {
    this.messages = messages;
    return this;
  }

  public ChatCharacterIdHistoryGet200Response addMessagesItem(ChatMessage messagesItem) {
    if (this.messages == null) {
      this.messages = new ArrayList<>();
    }
    this.messages.add(messagesItem);
    return this;
  }

  /**
   * Get messages
   * @return messages
   */
  @Valid 
  @Schema(name = "messages", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("messages")
  public List<@Valid ChatMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<@Valid ChatMessage> messages) {
    this.messages = messages;
  }

  public ChatCharacterIdHistoryGet200Response pagination(@Nullable WorldsGet200ResponsePagination pagination) {
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
    ChatCharacterIdHistoryGet200Response chatCharacterIdHistoryGet200Response = (ChatCharacterIdHistoryGet200Response) o;
    return Objects.equals(this.messages, chatCharacterIdHistoryGet200Response.messages) &&
        Objects.equals(this.pagination, chatCharacterIdHistoryGet200Response.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messages, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatCharacterIdHistoryGet200Response {\n");
    sb.append("    messages: ").append(toIndentedString(messages)).append("\n");
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

