package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ChatCharacterIdUserPostRequest
 */

@JsonTypeName("_chat__character_id__user_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class ChatCharacterIdUserPostRequest {

  private String message;

  private @Nullable String context;

  public ChatCharacterIdUserPostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ChatCharacterIdUserPostRequest(String message) {
    this.message = message;
  }

  public ChatCharacterIdUserPostRequest message(String message) {
    this.message = message;
    return this;
  }

  /**
   * �ъ슜�� 硫붿떆吏�
   * @return message
   */
  @NotNull @Size(min = 1, max = 1000) 
  @Schema(name = "message", description = "�ъ슜�� 硫붿떆吏�", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ChatCharacterIdUserPostRequest context(@Nullable String context) {
    this.context = context;
    return this;
  }

  /**
   * 異붽� 而⑦뀓�ㅽ듃 (�좏깮�ы빆)
   * @return context
   */
  @Size(max = 2000) 
  @Schema(name = "context", description = "異붽� 而⑦뀓�ㅽ듃 (�좏깮�ы빆)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public @Nullable String getContext() {
    return context;
  }

  public void setContext(@Nullable String context) {
    this.context = context;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatCharacterIdUserPostRequest chatCharacterIdUserPostRequest = (ChatCharacterIdUserPostRequest) o;
    return Objects.equals(this.message, chatCharacterIdUserPostRequest.message) &&
        Objects.equals(this.context, chatCharacterIdUserPostRequest.context);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, context);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatCharacterIdUserPostRequest {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
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

