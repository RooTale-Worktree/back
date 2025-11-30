package com.Rootale.universe.controller;

import com.Rootale.member.entity.CustomUser;
import com.Rootale.universe.dto.NarrativeDto;
import com.Rootale.universe.service.NarrativeService;
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
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@RestController
@RequestMapping("/narrative")
@RequiredArgsConstructor
@Tag(name = "Narrative", description = "내러티브(채팅) 관리 API")
@SecurityRequirement(name = "Bearer Authentication")
public class NarrativeController {

    private final NarrativeService narrativeService;
    private final Logger logger = LoggerFactory.getLogger(NarrativeController.class);

    @PostMapping("/{sessionId}/messages")
    @Operation(
            summary = "메시지 전송",
            description = "유저 메시지를 전송하고 AI 응답을 생성합니다. 이미지는 비동기로 생성되며 콜백 URL을 통해 폴링할 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메시지 전송 성공",
                            content = @Content(schema = @Schema(implementation = NarrativeDto.SendMessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "세션을 찾을 수 없음"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
            }
    )
    public Flux<ServerSentEvent<String>> sendMessageAndStream(
            @AuthenticationPrincipal CustomUser user,
            @Parameter(description = "세션 ID") @PathVariable String sessionId,
            @Valid @RequestBody NarrativeDto.SendMessageRequest request
    ) {
        Flux<String> tokenStream = narrativeService.sendMessageAndStream(
                user.getUserId(),
                sessionId,
                request
        );

        // 토큰 스트림을 SSE Event 형식으로 변환합니다.
        Flux<ServerSentEvent<String>> sseTokenFlux = tokenStream
                .map(token -> ServerSentEvent.<String>builder()
                        .data(token)
                        .id(String.valueOf(System.currentTimeMillis()))
                        .build());

        // 클라이언트가 식별하기 위한 종료 이벤트를 생성합니다.
        Mono<ServerSentEvent<String>> doneEventMono = Mono.just(
                ServerSentEvent.<String>builder()
                        .event("done") // 이벤트 이름: done
                        .data("")      // 빈 데이터 or 최종 상태 데이터
                        .id("final")
                        .build()
        );

        // 토큰 스트림 뒤에 종료 이벤트를 붙여서 반환합니다.
        // Flux.concat()은 sseTokenFlux가 ON_COMPLETE 시그널을 발행할 때까지 기다렸다가
        // doneEventMono의 이벤트를 발행하고, 최종적으로 스트림을 닫습니다.
        return Flux.concat(sseTokenFlux, doneEventMono)
                // 5. 로깅을 위한 doFinally는 유지 (필수 아님)
                .doFinally(signalType -> {
                    if (signalType.equals(reactor.core.publisher.SignalType.ON_COMPLETE)) {
                        logger.info("스트리밍 완료 및 연결 종료 (DONE 이벤트 전송됨)");
                    }
                });
    }

    @GetMapping("/callback/image/{callbackId}")
    @Operation(
            summary = "이미지 생성 콜백 조회",
            description = "이미지 생성 상태를 조회합니다. 클라이언트는 이 엔드포인트를 폴링하여 이미지 생성 완료를 확인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = NarrativeDto.ImageCallbackResponse.class))),
                    @ApiResponse(responseCode = "404", description = "콜백을 찾을 수 없음")
            }
    )
    public ResponseEntity<NarrativeDto.ImageCallbackResponse> getImageCallback(
            @Parameter(description = "콜백 ID") @PathVariable UUID callbackId
    ) {
        NarrativeDto.ImageCallbackResponse response = narrativeService.getImageCallback(callbackId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{sessionId}/tts")
    @Operation(
            summary = "TTS 생성",
            description = "특정 메시지에 대한 음성(TTS)을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "TTS 생성 성공",
                            content = @Content(schema = @Schema(implementation = NarrativeDto.TtsResponse.class))),
                    @ApiResponse(responseCode = "404", description = "메시지를 찾을 수 없음"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
            }
    )
    public ResponseEntity<NarrativeDto.TtsResponse> generateTts(
            @AuthenticationPrincipal CustomUser user,
            @Parameter(description = "세션 ID") @PathVariable String sessionId,
            @Valid @RequestBody NarrativeDto.TtsRequest request
    ) {
        NarrativeDto.TtsResponse response = narrativeService.generateTts(
                user.getUserId(),
                sessionId,
                request
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{sessionId}/history")
    @Operation(
            summary = "채팅 히스토리 조회",
            description = "특정 세션의 내러티브 메시지 히스토리를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = NarrativeDto.HistoryResponse.class))),
                    @ApiResponse(responseCode = "404", description = "세션을 찾을 수 없음"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
            }
    )
    public ResponseEntity<NarrativeDto.HistoryResponse> getHistory(
            @AuthenticationPrincipal CustomUser user,
            @Parameter(description = "세션 ID") @PathVariable String sessionId,
            @Parameter(description = "한 번에 조회할 메시지 개수") @RequestParam(defaultValue = "20") Integer limit,
            @Parameter(description = "건너뛸 메시지 개수") @RequestParam(defaultValue = "0") Integer offset
    ) {
        NarrativeDto.HistoryResponse response = narrativeService.getHistory(
                user.getUserId(),
                sessionId,
                limit,
                offset
        );
        return ResponseEntity.ok(response);
    }
}