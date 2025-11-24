package com.Rootale.system.dto;

import com.Rootale.member.entity.Feedback;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * System API 관련 모든 DTO를 포함하는 컨테이너 클래스
 */
public class SystemDto {

    // ==================== Response DTOs ====================

    /**
     * Health Check 응답
     */
    public record HealthResponse(
            String status,
            Instant timestamp,
            String version
    ) {
        public static HealthResponse healthy(String version) {
            return new HealthResponse("healthy", Instant.now(), version);
        }
    }

    /**
     * Feedback 생성 요청
     */
    public record CreateFeedbackRequest(
            @NotBlank(message = "카테고리는 필수입니다")
            @Size(max = 20)
            String category,

            @NotBlank(message = "제목은 필수입니다")
            @Size(max = 255, message = "제목은 255자를 넘을 수 없습니다")
            String title,

            @NotBlank(message = "내용은 필수입니다")
            @Size(max = 5000, message = "내용은 5000자를 넘을 수 없습니다")
            String content
    ) {}

    /**
     * Feedback 응답
     */
    public record FeedbackResponse(
            String id,
            @JsonProperty("user_id") String userId,
            String category,
            String title,
            String content,
            @JsonProperty("created_at") Instant createdAt
    ) {
        public static FeedbackResponse from(Feedback feedback) {
            return new FeedbackResponse(
                    feedback.getId() != null ? feedback.getId().toString() : null,
                    feedback.getUser() != null ? feedback.getUser().getUsersId().toString() : null,
                    feedback.getCategory() != null ? feedback.getCategory().name().toLowerCase() : null,
                    feedback.getTitle(),
                    feedback.getContent(),
                    feedback.getCreatedAt()
            );
        }
    }
}
