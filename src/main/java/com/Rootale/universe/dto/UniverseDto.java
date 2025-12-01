package com.Rootale.universe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class UniverseDto {

    /**
     * 세계관 생성 요청
     */
    public record CreateUniverseRequest(
            @NotBlank(message = "세계관 이름은 필수입니다")
            @Size(max = 255, message = "이름은 255자를 초과할 수 없습니다")
            String name,

            @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다")
            String description,

            @Size(max = 2000, message = "스토리는 2000자를 초과할 수 없습니다")
            String story,

            @Size(max = 2000, message = "정전은 2000자를 초과할 수 없습니다")
            String canon,

            @JsonProperty("representative_image")
            String representativeImage,

            @JsonProperty("estimated_play_time")
            Integer estimatedPlayTime
    ) {}

    /**
     * 세계관 생성 응답
     */
    public record CreateUniverseResponse(
            String id,
            String name,
            String description,
            String story,
            String canon,
            @JsonProperty("representative_image") String representativeImage,
            @JsonProperty("estimated_play_time") Integer estimatedPlayTime,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {}

    /**
     * 세계관 수정 요청
     */
    public record UpdateUniverseRequest(
            @Size(max = 255, message = "이름은 255자를 초과할 수 없습니다")
            String name,

            @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다")
            String description,

            @Size(max = 2000, message = "스토리는 2000자를 초과할 수 없습니다")
            String story,

            @Size(max = 2000, message = "정전은 2000자를 초과할 수 없습니다")
            String canon,

            @JsonProperty("representative_image")
            String representativeImage,

            @JsonProperty("estimated_play_time")
            Integer estimatedPlayTime
    ) {}

    /**
     * 세계관 목록 응답
     */
    public record UniverseListResponse(
            List<UniverseSummary> universes
    ) {}

    /**
     * 세계관 요약 정보
     */
    public record UniverseSummary(
            String id,
            String name,
            String description,
            @JsonProperty("representative_image") String representativeImage,
            @JsonProperty("estimated_play_time") Integer estimatedPlayTime,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {}

    /**
     * 세계관 상세 정보
     */
    public record UniverseDetailResponse(
            String id,
            String name,
            String description,
            @JsonProperty("representative_image") String representativeImage,
            @JsonProperty("estimated_play_time") Integer estimatedPlayTime,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {}

    /**
     * 캐릭터 목록 응답
     */
    public record CharacterListResponse(
            List<CharacterSummary> characters
    ) {}

    /**
     * 캐릭터 요약 정보
     */
    public record CharacterSummary(
            String id,
            @JsonProperty("universe_id") String universeId,
            String name,
            String description,
            @JsonProperty("avatar_url") String avatarUrl,
            String personality
    ) {}


    /**
     * 이미지 업로드 응답
     */
    public record ImageUploadResponse(
            @JsonProperty("s3_key") String s3Key,
            @JsonProperty("presigned_url") String presignedUrl
    ) {}
}