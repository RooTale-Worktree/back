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
 * StoriesStoryIdProgressPost200Response
 */

@JsonTypeName("_stories__story_id__progress_post_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-29T19:10:41.347886+09:00[Asia/Seoul]", comments = "Generator version: 7.16.0")
public class StoriesStoryIdProgressPost200Response {

  private @Nullable Scene nextScene;

  /**
   * Gets or Sets storyStatus
   */
  public enum StoryStatusEnum {
    ACTIVE("active"),
    
    PAUSED("paused"),
    
    COMPLETED("completed"),
    
    ABANDONED("abandoned");

    private final String value;

    StoryStatusEnum(String value) {
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
    public static StoryStatusEnum fromValue(String value) {
      for (StoryStatusEnum b : StoryStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable StoryStatusEnum storyStatus;

  @Valid
  private List<@Valid Achievement> achievements = new ArrayList<>();

  public StoriesStoryIdProgressPost200Response nextScene(@Nullable Scene nextScene) {
    this.nextScene = nextScene;
    return this;
  }

  /**
   * Get nextScene
   * @return nextScene
   */
  @Valid 
  @Schema(name = "next_scene", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("next_scene")
  public @Nullable Scene getNextScene() {
    return nextScene;
  }

  public void setNextScene(@Nullable Scene nextScene) {
    this.nextScene = nextScene;
  }

  public StoriesStoryIdProgressPost200Response storyStatus(@Nullable StoryStatusEnum storyStatus) {
    this.storyStatus = storyStatus;
    return this;
  }

  /**
   * Get storyStatus
   * @return storyStatus
   */
  
  @Schema(name = "story_status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("story_status")
  public @Nullable StoryStatusEnum getStoryStatus() {
    return storyStatus;
  }

  public void setStoryStatus(@Nullable StoryStatusEnum storyStatus) {
    this.storyStatus = storyStatus;
  }

  public StoriesStoryIdProgressPost200Response achievements(List<@Valid Achievement> achievements) {
    this.achievements = achievements;
    return this;
  }

  public StoriesStoryIdProgressPost200Response addAchievementsItem(Achievement achievementsItem) {
    if (this.achievements == null) {
      this.achievements = new ArrayList<>();
    }
    this.achievements.add(achievementsItem);
    return this;
  }

  /**
   * Get achievements
   * @return achievements
   */
  @Valid 
  @Schema(name = "achievements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("achievements")
  public List<@Valid Achievement> getAchievements() {
    return achievements;
  }

  public void setAchievements(List<@Valid Achievement> achievements) {
    this.achievements = achievements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoriesStoryIdProgressPost200Response storiesStoryIdProgressPost200Response = (StoriesStoryIdProgressPost200Response) o;
    return Objects.equals(this.nextScene, storiesStoryIdProgressPost200Response.nextScene) &&
        Objects.equals(this.storyStatus, storiesStoryIdProgressPost200Response.storyStatus) &&
        Objects.equals(this.achievements, storiesStoryIdProgressPost200Response.achievements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nextScene, storyStatus, achievements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoriesStoryIdProgressPost200Response {\n");
    sb.append("    nextScene: ").append(toIndentedString(nextScene)).append("\n");
    sb.append("    storyStatus: ").append(toIndentedString(storyStatus)).append("\n");
    sb.append("    achievements: ").append(toIndentedString(achievements)).append("\n");
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

