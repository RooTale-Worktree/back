
package com.Rootale.universe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

/**
 * Narrative API 관련 DTO
 */
public class NarrativeDto {

    /**
     * POST /narrative/{sessionId}/messages 요청
     */
    @Builder
    public record SendMessageRequest(
            @NotBlank(message = "메시지는 필수입니다")
            @Size(max = 2000, message = "메시지는 2000자를 넘을 수 없습니다")
            String message
    ) {}

    /**
     * POST /narrative/{sessionId}/messages 응답
     */
    @Builder
    public record SendMessageResponse(
            String text,

            @JsonProperty("text_callback_url")
            String textCallbackUrl,

            @JsonProperty("image_callback_url")
            String imageCallbackUrl
    ) {}

    /**
     * GET /narrative/callback/image/{callbackId} 응답
     */
    @Builder
    public record ImageCallbackResponse(
            String status,  // pending, completed, failed

            @JsonProperty("image_url")
            String imageUrl,

            String error
    ) {}

    /**
     * POST /narrative/{sessionId}/tts 요청
     */
    @Builder
    public record TtsRequest(
            @JsonProperty("message_id")
            @NotBlank(message = "message_id는 필수입니다")
            String messageId,

            @NotBlank(message = "text는 필수입니다")
            @Size(max = 2000, message = "텍스트는 2000자를 넘을 수 없습니다")
            String text,

            @JsonProperty("voice_type")
            String voiceType  // male, female, etc.
    ) {}

    /**
     * POST /narrative/{sessionId}/tts 응답
     */
    @Builder
    public record TtsResponse(
            @JsonProperty("audio_url")
            String audioUrl,

            @JsonProperty("message_id")
            String messageId
    ) {}

    /**
     * GET /narrative/{sessionId}/history 응답의 개별 메시지
     */
    @Builder
    public record MessageInfo(
            String id,

            @JsonProperty("session_id")
            String sessionId,

            String role,  // user, assistant

            String content,

            @JsonProperty("image_url")
            String imageUrl,

            @JsonProperty("audio_url")
            String audioUrl,

            @JsonProperty("created_at")
            Instant createdAt
    ) {}

    /**
     * GET /narrative/{sessionId}/history 응답
     */
    @Builder
    public record HistoryResponse(
            List<MessageInfo> messages,
            Integer total
    ) {}
}