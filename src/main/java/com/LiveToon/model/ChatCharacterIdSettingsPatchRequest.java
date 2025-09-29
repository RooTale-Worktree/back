package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ChatCharacterIdSettingsPatchRequest
 */

@JsonTypeName("_chat__character_id__settings_patch_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class ChatCharacterIdSettingsPatchRequest {

  /**
   * ���� ��
   */
  public enum ToneEnum {
    FORMAL("formal"),
    
    CASUAL("casual"),
    
    FRIENDLY("friendly"),
    
    PROFESSIONAL("professional"),
    
    PLAYFUL("playful");

    private final String value;

    ToneEnum(String value) {
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
    public static ToneEnum fromValue(String value) {
      for (ToneEnum b : ToneEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ToneEnum tone;

  /**
   * ���� �ㅽ���
   */
  public enum StyleEnum {
    CONVERSATIONAL("conversational"),
    
    NARRATIVE("narrative"),
    
    POETIC("poetic"),
    
    TECHNICAL("technical");

    private final String value;

    StyleEnum(String value) {
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
    public static StyleEnum fromValue(String value) {
      for (StyleEnum b : StyleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable StyleEnum style;

  @Valid
  private List<String> personalityEmphasis = new ArrayList<>();

  /**
   * �묐떟 湲몄씠 �좏샇��
   */
  public enum ResponseLengthEnum {
    SHORT("short"),
    
    MEDIUM("medium"),
    
    LONG("long");

    private final String value;

    ResponseLengthEnum(String value) {
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
    public static ResponseLengthEnum fromValue(String value) {
      for (ResponseLengthEnum b : ResponseLengthEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable ResponseLengthEnum responseLength;

  @Valid
  private List<String> topics = new ArrayList<>();

  public ChatCharacterIdSettingsPatchRequest tone(@Nullable ToneEnum tone) {
    this.tone = tone;
    return this;
  }

  /**
   * ���� ��
   * @return tone
   */
  
  @Schema(name = "tone", description = "���� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tone")
  public @Nullable ToneEnum getTone() {
    return tone;
  }

  public void setTone(@Nullable ToneEnum tone) {
    this.tone = tone;
  }

  public ChatCharacterIdSettingsPatchRequest style(@Nullable StyleEnum style) {
    this.style = style;
    return this;
  }

  /**
   * ���� �ㅽ���
   * @return style
   */
  
  @Schema(name = "style", description = "���� �ㅽ���", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("style")
  public @Nullable StyleEnum getStyle() {
    return style;
  }

  public void setStyle(@Nullable StyleEnum style) {
    this.style = style;
  }

  public ChatCharacterIdSettingsPatchRequest personalityEmphasis(List<String> personalityEmphasis) {
    this.personalityEmphasis = personalityEmphasis;
    return this;
  }

  public ChatCharacterIdSettingsPatchRequest addPersonalityEmphasisItem(String personalityEmphasisItem) {
    if (this.personalityEmphasis == null) {
      this.personalityEmphasis = new ArrayList<>();
    }
    this.personalityEmphasis.add(personalityEmphasisItem);
    return this;
  }

  /**
   * 媛뺤“�� �깃꺽 �뱀꽦
   * @return personalityEmphasis
   */
  
  @Schema(name = "personality_emphasis", description = "媛뺤“�� �깃꺽 �뱀꽦", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("personality_emphasis")
  public List<String> getPersonalityEmphasis() {
    return personalityEmphasis;
  }

  public void setPersonalityEmphasis(List<String> personalityEmphasis) {
    this.personalityEmphasis = personalityEmphasis;
  }

  public ChatCharacterIdSettingsPatchRequest responseLength(@Nullable ResponseLengthEnum responseLength) {
    this.responseLength = responseLength;
    return this;
  }

  /**
   * �묐떟 湲몄씠 �좏샇��
   * @return responseLength
   */
  
  @Schema(name = "response_length", description = "�묐떟 湲몄씠 �좏샇��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("response_length")
  public @Nullable ResponseLengthEnum getResponseLength() {
    return responseLength;
  }

  public void setResponseLength(@Nullable ResponseLengthEnum responseLength) {
    this.responseLength = responseLength;
  }

  public ChatCharacterIdSettingsPatchRequest topics(List<String> topics) {
    this.topics = topics;
    return this;
  }

  public ChatCharacterIdSettingsPatchRequest addTopicsItem(String topicsItem) {
    if (this.topics == null) {
      this.topics = new ArrayList<>();
    }
    this.topics.add(topicsItem);
    return this;
  }

  /**
   * �좏샇�섎뒗 ���� 二쇱젣
   * @return topics
   */
  
  @Schema(name = "topics", description = "�좏샇�섎뒗 ���� 二쇱젣", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("topics")
  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(List<String> topics) {
    this.topics = topics;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatCharacterIdSettingsPatchRequest chatCharacterIdSettingsPatchRequest = (ChatCharacterIdSettingsPatchRequest) o;
    return Objects.equals(this.tone, chatCharacterIdSettingsPatchRequest.tone) &&
        Objects.equals(this.style, chatCharacterIdSettingsPatchRequest.style) &&
        Objects.equals(this.personalityEmphasis, chatCharacterIdSettingsPatchRequest.personalityEmphasis) &&
        Objects.equals(this.responseLength, chatCharacterIdSettingsPatchRequest.responseLength) &&
        Objects.equals(this.topics, chatCharacterIdSettingsPatchRequest.topics);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tone, style, personalityEmphasis, responseLength, topics);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatCharacterIdSettingsPatchRequest {\n");
    sb.append("    tone: ").append(toIndentedString(tone)).append("\n");
    sb.append("    style: ").append(toIndentedString(style)).append("\n");
    sb.append("    personalityEmphasis: ").append(toIndentedString(personalityEmphasis)).append("\n");
    sb.append("    responseLength: ").append(toIndentedString(responseLength)).append("\n");
    sb.append("    topics: ").append(toIndentedString(topics)).append("\n");
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

