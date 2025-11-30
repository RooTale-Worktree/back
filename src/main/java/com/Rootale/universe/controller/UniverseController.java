package com.Rootale.universe.controller;

import com.Rootale.universe.dto.ErrorResponse;
import com.Rootale.universe.dto.UniverseDto;
import com.Rootale.universe.service.UniverseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/universes")
@RequiredArgsConstructor
@Tag(name = "Universe", description = "세계관 API")
public class UniverseController {

    private final UniverseService universeService;

    @GetMapping
    @Operation(summary = "전체 세계관 목록 조회", description = "모든 세계관의 목록을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UniverseDto.UniverseListResponse> getAllUniverses(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        UniverseDto.UniverseListResponse response = universeService.getAllUniverses();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{universe_id}")
    @Operation(summary = "세계관 상세 조회", description = "특정 세계관의 상세 정보를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "세계관을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UniverseDto.UniverseDetailResponse> getUniverseById(
            @PathVariable("universe_id") String universeId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        UniverseDto.UniverseDetailResponse response = universeService.getUniverseById(universeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{universe_id}/characters")
    @Operation(summary = "세계관의 캐릭터 목록 조회", description = "특정 세계관에 속한 캐릭터 목록을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "세계관을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UniverseDto.CharacterListResponse> getCharactersByUniverseId(
            @PathVariable("universe_id") String universeId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        UniverseDto.CharacterListResponse response = universeService.getCharactersByUniverseId(universeId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "새로운 세계관 생성", description = "새로운 세계관을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UniverseDto.CreateUniverseResponse> createUniverse(
            @Valid @RequestBody UniverseDto.CreateUniverseRequest request,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        UniverseDto.CreateUniverseResponse response = universeService.createUniverse(request);
        return ResponseEntity.ok(response);
    }
}