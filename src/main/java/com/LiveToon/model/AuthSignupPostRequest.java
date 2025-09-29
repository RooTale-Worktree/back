package com.LiveToon.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * AuthSignupPostRequest
 */

@JsonTypeName("_auth_signup_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class AuthSignupPostRequest {

  private String email;

  private String password;

  private String username;

  private String nickname;

  private @Nullable URI avatarUrl;

  public AuthSignupPostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AuthSignupPostRequest(String email, String password, String username, String nickname) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.nickname = nickname;
  }

  public AuthSignupPostRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * �대찓�� 二쇱냼
   * @return email
   */
  @NotNull @jakarta.validation.constraints.Email 
  @Schema(name = "email", description = "�대찓�� 二쇱냼", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public AuthSignupPostRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * 鍮꾨�踰덊샇 (理쒖냼 8��)
   * @return password
   */
  @NotNull @Size(min = 8) 
  @Schema(name = "password", description = "鍮꾨�踰덊샇 (理쒖냼 8��)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AuthSignupPostRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * �ъ슜�먮챸
   * @return username
   */
  @NotNull @Size(min = 3, max = 20) 
  @Schema(name = "username", description = "�ъ슜�먮챸", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AuthSignupPostRequest nickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  /**
   * �됰꽕��
   * @return nickname
   */
  @NotNull @Size(max = 30) 
  @Schema(name = "nickname", description = "�됰꽕��", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nickname")
  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public AuthSignupPostRequest avatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
    return this;
  }

  /**
   * �꾨줈�� �대�吏� URL (�좏깮�ы빆)
   * @return avatarUrl
   */
  @Valid 
  @Schema(name = "avatar_url", description = "�꾨줈�� �대�吏� URL (�좏깮�ы빆)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("avatar_url")
  public @Nullable URI getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(@Nullable URI avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthSignupPostRequest authSignupPostRequest = (AuthSignupPostRequest) o;
    return Objects.equals(this.email, authSignupPostRequest.email) &&
        Objects.equals(this.password, authSignupPostRequest.password) &&
        Objects.equals(this.username, authSignupPostRequest.username) &&
        Objects.equals(this.nickname, authSignupPostRequest.nickname) &&
        Objects.equals(this.avatarUrl, authSignupPostRequest.avatarUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password, username, nickname, avatarUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthSignupPostRequest {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    nickname: ").append(toIndentedString(nickname)).append("\n");
    sb.append("    avatarUrl: ").append(toIndentedString(avatarUrl)).append("\n");
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

