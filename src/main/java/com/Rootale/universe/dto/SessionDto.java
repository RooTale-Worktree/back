
package com.Rootale.universe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @Schema(
                    description = "세계관 ID",
                    example = "d16a682c-0b40-44cc-a328-935ffc31aa1e"
            )
            @JsonProperty("universe_id")
            @NotBlank(message = "universe_id는 필수입니다")
            String universeId,

            @Schema(
                    description = "세계관 ID",
                    example = "aae260b0-7700-4199-88f4-8ea8360d3deb"
            )
            @JsonProperty("character_id")
            @NotBlank(message = "character_id는 필수입니다")
            String characterId
    ) {}

    /**
     * POST /session 응답 - 세션 목록 반환
     */
    @Builder
    public record CreateSessionResponse(
            List<SessionInfo> sessions,
            Integer total
    ) {}

    /**
     * GET /session 응답의 개별 세션 정보
     */
    @Builder
    public record SessionInfo(
            @JsonProperty("session_id")
            String sessionId,  // ⭐ Long → String으로 변경 (UUID 형식)

            @JsonProperty("session_name")
            String sessionName,

            @JsonProperty("universe_id")
            String universeId,

            @JsonProperty("representative_image")
            String representativeImage,

            @JsonProperty("created_at")
            String createdAt,  // ⭐ ISO 8601 형식 문자열

            @JsonProperty("updated_at")
            String updatedAt   // ⭐ ISO 8601 형식 문자열
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