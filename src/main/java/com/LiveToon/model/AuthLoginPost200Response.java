package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * AuthLoginPost200Response
 */

@JsonTypeName("_auth_login_post_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class AuthLoginPost200Response {

  private @Nullable User user;

  private @Nullable String token;

  private @Nullable String refreshToken;

  private @Nullable Integer expiresIn;

  public AuthLoginPost200Response user(@Nullable User user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
   */
  @Valid 
  @Schema(name = "user", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("user")
  public @Nullable User getUser() {
    return user;
  }

  public void setUser(@Nullable User user) {
    this.user = user;
  }

  public AuthLoginPost200Response token(@Nullable String token) {
    this.token = token;
    return this;
  }

  /**
   * JWT �≪꽭�� �좏겙
   * @return token
   */
  
  @Schema(name = "token", description = "JWT �≪꽭�� �좏겙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("token")
  public @Nullable String getToken() {
    return token;
  }

  public void setToken(@Nullable String token) {
    this.token = token;
  }

  public AuthLoginPost200Response refreshToken(@Nullable String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * JWT 由ы봽�덉떆 �좏겙
   * @return refreshToken
   */
  
  @Schema(name = "refresh_token", description = "JWT 由ы봽�덉떆 �좏겙", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("refresh_token")
  public @Nullable String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(@Nullable String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public AuthLoginPost200Response expiresIn(@Nullable Integer expiresIn) {
    this.expiresIn = expiresIn;
    return this;
  }

  /**
   * �좏겙 留뚮즺 �쒓컙 (珥�)
   * @return expiresIn
   */
  
  @Schema(name = "expires_in", description = "�좏겙 留뚮즺 �쒓컙 (珥�)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expires_in")
  public @Nullable Integer getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(@Nullable Integer expiresIn) {
    this.expiresIn = expiresIn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthLoginPost200Response authLoginPost200Response = (AuthLoginPost200Response) o;
    return Objects.equals(this.user, authLoginPost200Response.user) &&
        Objects.equals(this.token, authLoginPost200Response.token) &&
        Objects.equals(this.refreshToken, authLoginPost200Response.refreshToken) &&
        Objects.equals(this.expiresIn, authLoginPost200Response.expiresIn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, token, refreshToken, expiresIn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthLoginPost200Response {\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
    sb.append("    expiresIn: ").append(toIndentedString(expiresIn)).append("\n");
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

