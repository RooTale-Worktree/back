package com.Rootale.universe.controller;

import com.Rootale.universe.dto.ErrorResponse;
import com.Rootale.universe.dto.SceneWithCandidatesDto;
import com.Rootale.universe.service.SceneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/scene")
@RequiredArgsConstructor
@Tag(name = "Scene", description = "Scene API")
public class SceneController {

    private final SceneService sceneService;

    @GetMapping
    @Operation(
            summary = "Scene 상세 조회 with 후보 노드",
            description = "nodeId로 Scene 정보와 PREREQUISITION 관계의 후보 Scene들을 조회합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Scene을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<?> getSceneWithCandidates(
            @RequestParam String nodeId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401)
                    .body(ErrorResponse.of("UNAUTHORIZED", "인증이 필요합니다"));
        }

        try {
            SceneWithCandidatesDto result = sceneService.getSceneWithCandidates(nodeId);
            return ResponseEntity.ok(result);
        } catch (SceneService.SceneNotFoundException e) {
            log.warn("⚠️ Scene not found: {}", nodeId);
            return ResponseEntity.status(404)
                    .body(ErrorResponse.of(
                            "SCENE_NOT_FOUND",
                            e.getMessage(),
                            Map.of("nodeId", nodeId)
                    ));
        } catch (Exception e) {
            log.error("❌ Error fetching scene: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                    .body(ErrorResponse.of(
                            "INTERNAL_SERVER_ERROR",
                            "서버 오류가 발생했습니다",
                            Map.of("details", e.getMessage())
                    ));
        }
    }
}