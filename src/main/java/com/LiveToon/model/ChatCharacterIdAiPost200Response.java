package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;

import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ChatCharacterIdAiPost200Response
 */

@JsonTypeName("_chat__character_id__ai_post_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class ChatCharacterIdAiPost200Response {

  private @Nullable ChatMessage reply;

  /**
   * 罹먮┃�� 媛먯젙 �곹깭
   */
  public enum CharacterEmotionEnum {
    HAPPY("happy"),
    
    SAD("sad"),
    
    ANGRY("angry"),
    
    SURPRISED("surprised"),
    
    NEUTRAL("neutral"),
    
    EXCITED("excited");

    private final String value;

    CharacterEmotionEnum(String value) {
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
    public static CharacterEmotionEnum fromValue(String value) {
      for (CharacterEmotionEnum b : CharacterEmotionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable CharacterEmotionEnum characterEmotion;

  private @Nullable BigDecimal responseTime;

  public ChatCharacterIdAiPost200Response reply(@Nullable ChatMessage reply) {
    this.reply = reply;
    return this;
  }

  /**
   * Get reply
   * @return reply
   */
  @Valid 
  @Schema(name = "reply", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reply")
  public @Nullable ChatMessage getReply() {
    return reply;
  }

  public void setReply(@Nullable ChatMessage reply) {
    this.reply = reply;
  }

  public ChatCharacterIdAiPost200Response characterEmotion(@Nullable CharacterEmotionEnum characterEmotion) {
    this.characterEmotion = characterEmotion;
    return this;
  }

  /**
   * 罹먮┃�� 媛먯젙 �곹깭
   * @return characterEmotion
   */
  
  @Schema(name = "character_emotion", description = "罹먮┃�� 媛먯젙 �곹깭", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("character_emotion")
  public @Nullable CharacterEmotionEnum getCharacterEmotion() {
    return characterEmotion;
  }

  public void setCharacterEmotion(@Nullable CharacterEmotionEnum characterEmotion) {
    this.characterEmotion = characterEmotion;
  }

  public ChatCharacterIdAiPost200Response responseTime(@Nullable BigDecimal responseTime) {
    this.responseTime = responseTime;
    return this;
  }

  /**
   * �묐떟 �앹꽦 �쒓컙 (珥�)
   * @return responseTime
   */
  @Valid 
  @Schema(name = "response_time", description = "�묐떟 �앹꽦 �쒓컙 (珥�)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("response_time")
  public @Nullable BigDecimal getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(@Nullable BigDecimal responseTime) {
    this.responseTime = responseTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatCharacterIdAiPost200Response chatCharacterIdAiPost200Response = (ChatCharacterIdAiPost200Response) o;
    return Objects.equals(this.reply, chatCharacterIdAiPost200Response.reply) &&
        Objects.equals(this.characterEmotion, chatCharacterIdAiPost200Response.characterEmotion) &&
        Objects.equals(this.responseTime, chatCharacterIdAiPost200Response.responseTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reply, characterEmotion, responseTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatCharacterIdAiPost200Response {\n");
    sb.append("    reply: ").append(toIndentedString(reply)).append("\n");
    sb.append("    characterEmotion: ").append(toIndentedString(characterEmotion)).append("\n");
    sb.append("    responseTime: ").append(toIndentedString(responseTime)).append("\n");
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

