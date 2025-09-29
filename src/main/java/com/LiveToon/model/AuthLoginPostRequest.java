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
 * AuthLoginPostRequest
 */

@JsonTypeName("_auth_login_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class AuthLoginPostRequest {

  private String email;

  private String password;

  private @Nullable Boolean rememberMe;

  public AuthLoginPostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AuthLoginPostRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public AuthLoginPostRequest email(String email) {
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

  public AuthLoginPostRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * 鍮꾨�踰덊샇
   * @return password
   */
  @NotNull 
  @Schema(name = "password", description = "鍮꾨�踰덊샇", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AuthLoginPostRequest rememberMe(@Nullable Boolean rememberMe) {
    this.rememberMe = rememberMe;
    return this;
  }

  /**
   * 濡쒓렇�� �곹깭 �좎� �щ�
   * @return rememberMe
   */
  
  @Schema(name = "remember_me", description = "濡쒓렇�� �곹깭 �좎� �щ�", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("remember_me")
  public @Nullable Boolean getRememberMe() {
    return rememberMe;
  }

  public void setRememberMe(@Nullable Boolean rememberMe) {
    this.rememberMe = rememberMe;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthLoginPostRequest authLoginPostRequest = (AuthLoginPostRequest) o;
    return Objects.equals(this.email, authLoginPostRequest.email) &&
        Objects.equals(this.password, authLoginPostRequest.password) &&
        Objects.equals(this.rememberMe, authLoginPostRequest.rememberMe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password, rememberMe);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthLoginPostRequest {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    rememberMe: ").append(toIndentedString(rememberMe)).append("\n");
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

