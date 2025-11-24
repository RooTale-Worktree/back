package com.Rootale.universe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class UniverseDto {

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
}