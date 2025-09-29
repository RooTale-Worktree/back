package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;

import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ChatCharacterIdUserPost201Response
 */

@JsonTypeName("_chat__character_id__user_post_201_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class ChatCharacterIdUserPost201Response {

  private @Nullable ChatMessage message;

  private @Nullable UUID conversationId;

  public ChatCharacterIdUserPost201Response message(@Nullable ChatMessage message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   */
  @Valid 
  @Schema(name = "message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public @Nullable ChatMessage getMessage() {
    return message;
  }

  public void setMessage(@Nullable ChatMessage message) {
    this.message = message;
  }

  public ChatCharacterIdUserPost201Response conversationId(@Nullable UUID conversationId) {
    this.conversationId = conversationId;
    return this;
  }

  /**
   * ���� �몄뀡 ID
   * @return conversationId
   */
  @Valid 
  @Schema(name = "conversation_id", description = "���� �몄뀡 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conversation_id")
  public @Nullable UUID getConversationId() {
    return conversationId;
  }

  public void setConversationId(@Nullable UUID conversationId) {
    this.conversationId = conversationId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatCharacterIdUserPost201Response chatCharacterIdUserPost201Response = (ChatCharacterIdUserPost201Response) o;
    return Objects.equals(this.message, chatCharacterIdUserPost201Response.message) &&
        Objects.equals(this.conversationId, chatCharacterIdUserPost201Response.conversationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, conversationId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatCharacterIdUserPost201Response {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    conversationId: ").append(toIndentedString(conversationId)).append("\n");
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

