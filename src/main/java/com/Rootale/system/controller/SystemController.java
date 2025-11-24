package com.Rootale.system.controller;

import com.Rootale.member.entity.CustomUser;
import com.Rootale.system.dto.SystemDto.*;
import com.Rootale.system.service.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Tag(name = "System", description = "시스템 관련 API (Health Check, Feedback)")
public class SystemController {

    private final SystemService systemService;

    @Operation(
            summary = "Health Check",
            description = "서버 상태를 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "서버 정상")
    })
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> healthCheck() {
        log.info("🏥 GET /system/health");
        HealthResponse response = systemService.healthCheck();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "피드백 제출",
            description = "버그 신고, 기능 제안 등의 피드백을 제출합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "피드백 제출 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponse> createFeedback(
            @AuthenticationPrincipal CustomUser currentUser,
            @Valid @RequestBody CreateFeedbackRequest request
    ) {
        if (currentUser == null) {
            log.warn("⚠️ Unauthenticated feedback submission attempt");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("📝 POST /system/feedback - userId: {}, category: {}",
                currentUser.getUserId(), request.category());

        try {
            FeedbackResponse response = systemService.createFeedback(currentUser.getUserId(), request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("❌ Invalid feedback request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 에러 핸들러
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        log.error("❌ Validation error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "BAD_REQUEST",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        log.error("❌ Unexpected error: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "INTERNAL_SERVER_ERROR",
                        "message", "서버 처리 중 오류가 발생했습니다."
                ));
    }
}
