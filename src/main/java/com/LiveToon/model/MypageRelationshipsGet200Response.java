package com.LiveToon.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * MypageRelationshipsGet200Response
 */

@JsonTypeName("_mypage_relationships_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageRelationshipsGet200Response {

  @Valid
  private List<@Valid MypageRelationshipsGet200ResponseCharacterRelationshipsInner> characterRelationships = new ArrayList<>();

  private @Nullable Integer totalCharacters;

  private @Nullable MypageRelationshipsGet200ResponseMostInteracted mostInteracted;

  public MypageRelationshipsGet200Response characterRelationships(List<@Valid MypageRelationshipsGet200ResponseCharacterRelationshipsInner> characterRelationships) {
    this.characterRelationships = characterRelationships;
    return this;
  }

  public MypageRelationshipsGet200Response addCharacterRelationshipsItem(MypageRelationshipsGet200ResponseCharacterRelationshipsInner characterRelationshipsItem) {
    if (this.characterRelationships == null) {
      this.characterRelationships = new ArrayList<>();
    }
    this.characterRelationships.add(characterRelationshipsItem);
    return this;
  }

  /**
   * Get characterRelationships
   * @return characterRelationships
   */
  @Valid 
  @Schema(name = "character_relationships", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("character_relationships")
  public List<@Valid MypageRelationshipsGet200ResponseCharacterRelationshipsInner> getCharacterRelationships() {
    return characterRelationships;
  }

  public void setCharacterRelationships(List<@Valid MypageRelationshipsGet200ResponseCharacterRelationshipsInner> characterRelationships) {
    this.characterRelationships = characterRelationships;
  }

  public MypageRelationshipsGet200Response totalCharacters(@Nullable Integer totalCharacters) {
    this.totalCharacters = totalCharacters;
    return this;
  }

  /**
   * 珥� �곹샇�묒슜�� 罹먮┃�� ��
   * @return totalCharacters
   */
  
  @Schema(name = "total_characters", description = "珥� �곹샇�묒슜�� 罹먮┃�� ��", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total_characters")
  public @Nullable Integer getTotalCharacters() {
    return totalCharacters;
  }

  public void setTotalCharacters(@Nullable Integer totalCharacters) {
    this.totalCharacters = totalCharacters;
  }

  public MypageRelationshipsGet200Response mostInteracted(@Nullable MypageRelationshipsGet200ResponseMostInteracted mostInteracted) {
    this.mostInteracted = mostInteracted;
    return this;
  }

  /**
   * Get mostInteracted
   * @return mostInteracted
   */
  @Valid 
  @Schema(name = "most_interacted", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("most_interacted")
  public @Nullable MypageRelationshipsGet200ResponseMostInteracted getMostInteracted() {
    return mostInteracted;
  }

  public void setMostInteracted(@Nullable MypageRelationshipsGet200ResponseMostInteracted mostInteracted) {
    this.mostInteracted = mostInteracted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageRelationshipsGet200Response mypageRelationshipsGet200Response = (MypageRelationshipsGet200Response) o;
    return Objects.equals(this.characterRelationships, mypageRelationshipsGet200Response.characterRelationships) &&
        Objects.equals(this.totalCharacters, mypageRelationshipsGet200Response.totalCharacters) &&
        Objects.equals(this.mostInteracted, mypageRelationshipsGet200Response.mostInteracted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(characterRelationships, totalCharacters, mostInteracted);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageRelationshipsGet200Response {\n");
    sb.append("    characterRelationships: ").append(toIndentedString(characterRelationships)).append("\n");
    sb.append("    totalCharacters: ").append(toIndentedString(totalCharacters)).append("\n");
    sb.append("    mostInteracted: ").append(toIndentedString(mostInteracted)).append("\n");
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

