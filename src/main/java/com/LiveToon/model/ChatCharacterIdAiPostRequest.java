package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ChatCharacterIdAiPostRequest
 */

@JsonTypeName("_chat__character_id__ai_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class ChatCharacterIdAiPostRequest {

  private @Nullable UUID conversationId;

  private @Nullable UUID lastMessageId;

  private Integer maxLength = 500;

  public ChatCharacterIdAiPostRequest conversationId(@Nullable UUID conversationId) {
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

  public ChatCharacterIdAiPostRequest lastMessageId(@Nullable UUID lastMessageId) {
    this.lastMessageId = lastMessageId;
    return this;
  }

  /**
   * 留덉�留� 硫붿떆吏� ID
   * @return lastMessageId
   */
  @Valid 
  @Schema(name = "last_message_id", description = "留덉�留� 硫붿떆吏� ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("last_message_id")
  public @Nullable UUID getLastMessageId() {
    return lastMessageId;
  }

  public void setLastMessageId(@Nullable UUID lastMessageId) {
    this.lastMessageId = lastMessageId;
  }

  public ChatCharacterIdAiPostRequest maxLength(Integer maxLength) {
    this.maxLength = maxLength;
    return this;
  }

  /**
   * 理쒕� �묐떟 湲몄씠
   * minimum: 50
   * maximum: 2000
   * @return maxLength
   */
  @Min(50) @Max(2000) 
  @Schema(name = "max_length", description = "理쒕� �묐떟 湲몄씠", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_length")
  public Integer getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatCharacterIdAiPostRequest chatCharacterIdAiPostRequest = (ChatCharacterIdAiPostRequest) o;
    return Objects.equals(this.conversationId, chatCharacterIdAiPostRequest.conversationId) &&
        Objects.equals(this.lastMessageId, chatCharacterIdAiPostRequest.lastMessageId) &&
        Objects.equals(this.maxLength, chatCharacterIdAiPostRequest.maxLength);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conversationId, lastMessageId, maxLength);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatCharacterIdAiPostRequest {\n");
    sb.append("    conversationId: ").append(toIndentedString(conversationId)).append("\n");
    sb.append("    lastMessageId: ").append(toIndentedString(lastMessageId)).append("\n");
    sb.append("    maxLength: ").append(toIndentedString(maxLength)).append("\n");
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

