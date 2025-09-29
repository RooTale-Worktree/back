package com.LiveToon.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * User
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class User {

  private @Nullable UUID id;

  private @Nullable String email;

  private @Nullable String username;

  private @Nullable String nickname;

  private @Nullable URI avatarUrl;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  private @Nullable Boolean isActive;

  /**
   * 援щ룆 �깃툒
   */
  public enum SubscriptionTierEnum {
    FREE("free"),
    
    PREMIUM("premium"),
    
    PRO("pro");

    private final String value;

    SubscriptionTierEnum(String value) {
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
    public static SubscriptionTierEnum fromValue(String value) {
      for (SubscriptionTierEnum b : SubscriptionTierEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable SubscriptionTierEnum subscriptionTier;

  public User id(@Nullable UUID id) {
    this.id = id;
    return this;
  }

  /**
   * �ъ슜�� 怨좎쑀 ID
   * @return id
   */
  @Valid 
  @Schema(name = "id", description = "�ъ슜�� 怨좎쑀 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable UUID getId() {
    return id;
  }

  public void setId(@Nullable UUID id) {
    this.id = id;
  }

  public User email(@Nullable String email) {
    this.email = email;
    return this;
  }

  /**
   * �대찓�� 二쇱냼
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", description = "�대찓�� 二쇱냼", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public @Nullable String getEmail() {
    return email;
  }

  public void setEmail(@Nullable String email) {
    this.email = email;
  }

  public User username(@Nullable String username) {
    this.username = username;
    return this;
  }

  /**
   * �ъ슜�먮챸
   * @return username
   */
  @Size(min = 3, max = 20) 
  @Schema(name = "username", description = "�ъ슜�먮챸", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public @Nullable String getUsername() {
    return username;
  }

  public void setUsername(@Nullable String username) {
    this.username = username;
  }

  public User nickname(@Nullable String nickname) {
    this.nickname = nickname;
    return this;
  }

  /**
   * �됰꽕��
   * @return nickname
   */
  @Size(max = 30) 
  @Schema(name = "nickname", description = "�됰꽕��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nickname")
  public @Nullable String getNickname() {
    return nickname;
  }

  public void setNickname(@Nullable String nickname) {
    this.nickname = nickname;
  }

  public User avatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
    return this;
  }

  /**
   * �꾨줈�� �대�吏� URL
   * @return avatarUrl
   */
  @Valid 
  @Schema(name = "avatar_url", description = "�꾨줈�� �대�吏� URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("avatar_url")
  public @Nullable URI getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public User createdAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * 怨꾩젙 �앹꽦��
   * @return createdAt
   */
  @Valid 
  @Schema(name = "created_at", description = "怨꾩젙 �앹꽦��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("created_at")
  public @Nullable OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public User updatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * 留덉�留� �섏젙��
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updated_at", description = "留덉�留� �섏젙��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updated_at")
  public @Nullable OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public User isActive(@Nullable Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  /**
   * 怨꾩젙 �쒖꽦�� �곹깭
   * @return isActive
   */
  
  @Schema(name = "is_active", description = "怨꾩젙 �쒖꽦�� �곹깭", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("is_active")
  public @Nullable Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(@Nullable Boolean isActive) {
    this.isActive = isActive;
  }

  public User subscriptionTier(@Nullable SubscriptionTierEnum subscriptionTier) {
    this.subscriptionTier = subscriptionTier;
    return this;
  }

  /**
   * 援щ룆 �깃툒
   * @return subscriptionTier
   */
  
  @Schema(name = "subscription_tier", description = "援щ룆 �깃툒", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subscription_tier")
  public @Nullable SubscriptionTierEnum getSubscriptionTier() {
    return subscriptionTier;
  }

  public void setSubscriptionTier(@Nullable SubscriptionTierEnum subscriptionTier) {
    this.subscriptionTier = subscriptionTier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.username, user.username) &&
        Objects.equals(this.nickname, user.nickname) &&
        Objects.equals(this.avatarUrl, user.avatarUrl) &&
        Objects.equals(this.createdAt, user.createdAt) &&
        Objects.equals(this.updatedAt, user.updatedAt) &&
        Objects.equals(this.isActive, user.isActive) &&
        Objects.equals(this.subscriptionTier, user.subscriptionTier);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, username, nickname, avatarUrl, createdAt, updatedAt, isActive, subscriptionTier);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    nickname: ").append(toIndentedString(nickname)).append("\n");
    sb.append("    avatarUrl: ").append(toIndentedString(avatarUrl)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    subscriptionTier: ").append(toIndentedString(subscriptionTier)).append("\n");
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

