package com.Rootale.universe.controller;

import com.Rootale.member.entity.CustomUser;
import com.Rootale.universe.dto.SessionDto;
import com.Rootale.universe.service.SessionService;
import com.Rootale.universe.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Tag(name = "Session", description = "세션(플레이) 관리 API")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @Operation(summary = "새 세션 생성", description = "새로운 플레이 세션을 생성하고 전체 세션 목록을 반환합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SessionDto.CreateSessionResponse> createSession(
            @Valid @RequestBody SessionDto.CreateSessionRequest request,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        // ⭐ CustomUser에서 userId 추출
        Long userId = extractUserId(authentication);
        SessionDto.CreateSessionResponse response = sessionService.createSession(userId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "세션 목록 조회", description = "사용자의 모든 플레이 세션을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SessionDto.SessionListResponse> getSessions(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        Long userId = extractUserId(authentication);
        SessionDto.CreateSessionResponse response = sessionService.getAllSessions(userId);

        // CreateSessionResponse와 SessionListResponse 구조가 동일하므로 재사용
        SessionDto.SessionListResponse listResponse = SessionDto.SessionListResponse.builder()
                .sessions(response.sessions())
                .total(response.total())
                .build();

        return ResponseEntity.ok(listResponse);
    }

    @DeleteMapping("/{session_id}")
    @Operation(summary = "세션 삭제", description = "특정 플레이 세션을 삭제합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "세션을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<SessionDto.DeleteSessionResponse> deleteSession(
            @PathVariable("session_id") String sessionId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        Long userId = extractUserId(authentication);
        SessionDto.DeleteSessionResponse response = sessionService.deleteSession(userId, sessionId);

        return ResponseEntity.ok(response);
    }

    private Long extractUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUser customUser) {
            return customUser.getUserId();
        }

        // CustomUser가 아닌 경우 (예: 테스트 환경)
        log.warn("⚠️ Principal is not CustomUser: {}", principal.getClass().getName());
        throw new IllegalStateException("Invalid authentication principal type");
    }
}