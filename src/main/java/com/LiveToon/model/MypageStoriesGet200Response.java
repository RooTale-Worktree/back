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
 * MypageStoriesGet200Response
 */

@JsonTypeName("_mypage_stories_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class MypageStoriesGet200Response {

  @Valid
  private List<MypageStoriesGet200ResponseStoriesInner> stories = new ArrayList<>();

  private @Nullable WorldsGet200ResponsePagination pagination;

  public MypageStoriesGet200Response stories(List<MypageStoriesGet200ResponseStoriesInner> stories) {
    this.stories = stories;
    return this;
  }

  public MypageStoriesGet200Response addStoriesItem(MypageStoriesGet200ResponseStoriesInner storiesItem) {
    if (this.stories == null) {
      this.stories = new ArrayList<>();
    }
    this.stories.add(storiesItem);
    return this;
  }

  /**
   * Get stories
   * @return stories
   */
  @Valid 
  @Schema(name = "stories", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stories")
  public List<MypageStoriesGet200ResponseStoriesInner> getStories() {
    return stories;
  }

  public void setStories(List<MypageStoriesGet200ResponseStoriesInner> stories) {
    this.stories = stories;
  }

  public MypageStoriesGet200Response pagination(@Nullable WorldsGet200ResponsePagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   * @return pagination
   */
  @Valid 
  @Schema(name = "pagination", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pagination")
  public @Nullable WorldsGet200ResponsePagination getPagination() {
    return pagination;
  }

  public void setPagination(@Nullable WorldsGet200ResponsePagination pagination) {
    this.pagination = pagination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MypageStoriesGet200Response mypageStoriesGet200Response = (MypageStoriesGet200Response) o;
    return Objects.equals(this.stories, mypageStoriesGet200Response.stories) &&
        Objects.equals(this.pagination, mypageStoriesGet200Response.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stories, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MypageStoriesGet200Response {\n");
    sb.append("    stories: ").append(toIndentedString(stories)).append("\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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

