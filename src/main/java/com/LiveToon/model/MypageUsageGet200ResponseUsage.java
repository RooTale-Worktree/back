package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageUsageGet200ResponseUsage
 */

@JsonTypeName("_mypage_usage_get_200_response_usage")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageUsageGet200ResponseUsage {

  private @Nullable Integer storiesCreated;

  private @Nullable Integer charactersCreated;

  private @Nullable Integer chatMessagesSent;

  private @Nullable Integer imagesGenerated;

  public MypageUsageGet200ResponseUsage storiesCreated(@Nullable Integer storiesCreated) {
    this.storiesCreated = storiesCreated;
    return this;
  }

  /**
   * �앹꽦�� �ㅽ넗由� ��
   * @return storiesCreated
   */
  
  @Schema(name = "stories_created", description = "�앹꽦�� �ㅽ넗由� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stories_created")
  public @Nullable Integer getStoriesCreated() {
    return storiesCreated;
  }

  public void setStoriesCreated(@Nullable Integer storiesCreated) {
    this.storiesCreated = storiesCreated;
  }

  public MypageUsageGet200ResponseUsage charactersCreated(@Nullable Integer charactersCreated) {
    this.charactersCreated = charactersCreated;
    return this;
  }

  /**
   * �앹꽦�� 罹먮┃�� ��
   * @return charactersCreated
   */
  
  @Schema(name = "characters_created", description = "�앹꽦�� 罹먮┃�� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("characters_created")
  public @Nullable Integer getCharactersCreated() {
    return charactersCreated;
  }

  public void setCharactersCreated(@Nullable Integer charactersCreated) {
    this.charactersCreated = charactersCreated;
  }

  public MypageUsageGet200ResponseUsage chatMessagesSent(@Nullable Integer chatMessagesSent) {
    this.chatMessagesSent = chatMessagesSent;
    return this;
  }

  /**
   * �꾩넚�� 梨꾪똿 硫붿떆吏� ��
   * @return chatMessagesSent
   */
  
  @Schema(name = "chat_messages_sent", description = "�꾩넚�� 梨꾪똿 硫붿떆吏� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("chat_messages_sent")
  public @Nullable Integer getChatMessagesSent() {
    return chatMessagesSent;
  }

  public void setChatMessagesSent(@Nullable Integer chatMessagesSent) {
    this.chatMessagesSent = chatMessagesSent;
  }

  public MypageUsageGet200ResponseUsage imagesGenerated(@Nullable Integer imagesGenerated) {
    this.imagesGenerated = imagesGenerated;
    return this;
  }

  /**
   * �앹꽦�� �대�吏� ��
   * @return imagesGenerated
   */
  
  @Schema(name = "images_generated", description = "�앹꽦�� �대�吏� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("images_generated")
  public @Nullable Integer getImagesGenerated() {
    return imagesGenerated;
  }

  public void setImagesGenerated(@Nullable Integer imagesGenerated) {
    this.imagesGenerated = imagesGenerated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageUsageGet200ResponseUsage mypageUsageGet200ResponseUsage = (MypageUsageGet200ResponseUsage) o;
    return Objects.equals(this.storiesCreated, mypageUsageGet200ResponseUsage.storiesCreated) &&
        Objects.equals(this.charactersCreated, mypageUsageGet200ResponseUsage.charactersCreated) &&
        Objects.equals(this.chatMessagesSent, mypageUsageGet200ResponseUsage.chatMessagesSent) &&
        Objects.equals(this.imagesGenerated, mypageUsageGet200ResponseUsage.imagesGenerated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storiesCreated, charactersCreated, chatMessagesSent, imagesGenerated);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageUsageGet200ResponseUsage {\n");
    sb.append("    storiesCreated: ").append(toIndentedString(storiesCreated)).append("\n");
    sb.append("    charactersCreated: ").append(toIndentedString(charactersCreated)).append("\n");
    sb.append("    chatMessagesSent: ").append(toIndentedString(chatMessagesSent)).append("\n");
    sb.append("    imagesGenerated: ").append(toIndentedString(imagesGenerated)).append("\n");
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

