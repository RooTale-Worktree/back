
package com.Rootale.universe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

/**
 * Session API 관련 DTO
 */
public class SessionDto {

    /**
     * POST /session 요청
     */
    @Builder
    public record CreateSessionRequest(
            @JsonProperty("universe_id")
            @NotBlank(message = "universe_id는 필수입니다")
            String universeId,

            @JsonProperty("character_id")
            @NotBlank(message = "character_id는 필수입니다")
            String characterId
    ) {}

    /**
     * POST /session 응답
     */
    @Builder
    public record CreateSessionResponse(
            @JsonProperty("first_message")
            String firstMessage,

            @JsonProperty("first_image")
            String firstImage
    ) {}

    /**
     * GET /session 응답의 개별 세션 정보
     */
    @Builder
    public record SessionInfo(
            @JsonProperty("session_id")
            String sessionId,

            @JsonProperty("session_name")
            String sessionName,

            @JsonProperty("universe_id")
            String universeId,

            @JsonProperty("representative_image")
            String representativeImage,

            @JsonProperty("created_at")
            Instant createdAt,

            @JsonProperty("updated_at")
            Instant updatedAt
    ) {}

    /**
     * GET /session 응답
     */
    @Builder
    public record SessionListResponse(
            List<SessionInfo> sessions,
            Integer total
    ) {}

    /**
     * DELETE /session/{sessionId} 응답
     */
    @Builder
    public record DeleteSessionResponse(
            String message
    ) {}
}