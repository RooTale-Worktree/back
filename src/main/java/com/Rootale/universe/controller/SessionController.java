
package com.Rootale.universe.controller;

import com.Rootale.member.entity.CustomUser;
import com.Rootale.universe.dto.SessionDto;
import com.Rootale.universe.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Tag(name = "Session", description = "세션(플레이) 관리 API")
@SecurityRequirement(name = "Bearer Authentication")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @Operation(
            summary = "세션 생성",
            description = "새로운 플레이 세션을 시작합니다. Universe와 Character를 선택하여 게임을 시작합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "세션 생성 성공",
                            content = @Content(schema = @Schema(implementation = SessionDto.CreateSessionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Universe 또는 Character를 찾을 수 없음"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
            }
    )
    public ResponseEntity<SessionDto.CreateSessionResponse> createSession(
            @AuthenticationPrincipal CustomUser user,
            @Valid @RequestBody SessionDto.CreateSessionRequest request
    ) {
        SessionDto.CreateSessionResponse response = sessionService.createSession(
                user.getUserId().intValue(),
                request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "세션 목록 조회",
            description = "사용자의 모든 플레이 세션 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = SessionDto.SessionListResponse.class))),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
            }
    )
    public ResponseEntity<SessionDto.SessionListResponse> getSessions(
            @AuthenticationPrincipal CustomUser user,
            @Parameter(description = "한 번에 조회할 세션 개수") @RequestParam(defaultValue = "10") Integer limit,
            @Parameter(description = "건너뛸 세션 개수") @RequestParam(defaultValue = "0") Integer offset
    ) {
        SessionDto.SessionListResponse response = sessionService.getSessions(
                user.getUserId().intValue(),
                limit,
                offset
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sessionId}")
    @Operation(
            summary = "세션 삭제",
            description = "특정 플레이 세션을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공",
                            content = @Content(schema = @Schema(implementation = SessionDto.DeleteSessionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "세션을 찾을 수 없음"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
            }
    )
    public ResponseEntity<SessionDto.DeleteSessionResponse> deleteSession(
            @AuthenticationPrincipal CustomUser user,
            @Parameter(description = "삭제할 세션 ID") @PathVariable String sessionId
    ) {
        SessionDto.DeleteSessionResponse response = sessionService.deleteSession(
                user.getUserId().intValue(),
                sessionId
        );
        return ResponseEntity.ok(response);
    }
}