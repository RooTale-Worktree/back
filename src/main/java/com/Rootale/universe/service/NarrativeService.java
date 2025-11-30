package com.Rootale.universe.service;

import com.Rootale.member.entity.ImageGenerationCallback;
import com.Rootale.member.entity.NarrativeMessage;
import com.Rootale.member.entity.User;
import com.Rootale.member.repository.ImageGenerationCallbackRepository;
import com.Rootale.member.repository.NarrativeMessageRepository;
import com.Rootale.member.repository.UserRepository;
import com.Rootale.universe.dto.LlmDto;
import com.Rootale.universe.dto.NarrativeDto;
import com.Rootale.universe.entity.Character;
import com.Rootale.universe.entity.Universe;
import com.Rootale.universe.entity.UserNode;
import com.Rootale.universe.exception.ResourceNotFoundException;
import com.Rootale.universe.repository.CharacterRepository;
import com.Rootale.universe.repository.UniverseRepository;
import com.Rootale.universe.repository.UserNodeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NarrativeService {

    private final NarrativeMessageRepository messageRepository;
    private final ImageGenerationCallbackRepository callbackRepository;
    private final UserNodeRepository userNodeRepository;
    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final WebClient llmWebClient;
    private final WebClient ttsWebClient;
    private final WebClient imageWebClient;
    private final UniverseRepository universeRepository;
    private final ObjectMapper objectMapper;

    /**
     * 유저 메시지 전송 및 AI 응답 스트림 생성
     * LLM에서 받은 토큰 스트림을 반환합니다. 모든 DB 블로킹 작업은 별도의 스레드에서 수행됩니다.
     */
    @Transactional
    public Flux<String> sendMessageAndStream(
            Long userId,
            String sessionId,
            NarrativeDto.SendMessageRequest request
    ) {
        // 1. 초기 DB 작업 - User와 LLM Request를 함께(context) 반환
        Mono<LlmDto.MessageContext> initialDbWork = Mono.fromCallable(() -> {
            // 1-1. 유저 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

            // 1-2. 세션 검증 (Neo4j)
            UserNode userNode = userNodeRepository.findByUserId(userId.intValue())
                    .orElseThrow(() -> new ResourceNotFoundException("UserNode not found: " + userId));

            userNode.getPlayRelationships().stream()
                    .filter(play -> play.getId().equals(sessionId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Session not found: " + sessionId));

            // 1-3. Universe & Character 조회
            Universe universe = universeRepository.findUniverseBySessionId(userId, sessionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Universe not found:" + sessionId));

            Character character = characterRepository.findInteractingCharactersByUniverseId(userId, universe.getUniverseId());

            // 1-4. Chat History 조회
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<NarrativeMessage> chatHistory = messageRepository.findBySessionIdOrderByCreatedAtDesc(sessionId, pageable);

            // 1-5. LLM Request DTO 생성 (User 객체 포함)
            LlmDto.Request llmRequest = LlmDto.buildLlmRequestDto(
                    user, universe, character, chatHistory, request.message());

            // 1-6. 유저 메시지 저장
            NarrativeMessage userMessage = NarrativeMessage.builder()
                    .sessionId(sessionId)
                    .role("user")
                    .content(request.message())
                    .user(user)
                    .build();
            messageRepository.save(userMessage);
            log.info("User message saved. SessionId: {}, UserId: {}", sessionId, userId);

            return LlmDto.MessageContext.builder()
                    .user(user)
                    .llmRequest(llmRequest)
                    .build();

        }).subscribeOn(Schedulers.boundedElastic());

        // 2. LLM RunPod API 호출 파이프라인
        return initialDbWork.flatMapMany(context -> {

            // 2-1. /run API 호출: job ID 획득
            Mono<String> jobIdMono = callRunApi(context.llmRequest())
                    .map(LlmDto.RunResponse::id)
                    .doOnSuccess(jobId -> log.info("Job created. JobId: {}", jobId));

            // 2-2. Job ID를 받아 스트리밍 및 최종 저장 로직 실행
            return jobIdMono.flatMapMany(jobId -> {
                StringBuilder llmResponseContent = new StringBuilder();

                // 2-3. 최종 저장을 위한 메시지 엔티티 준비 (User 객체 사용 가능!)
                NarrativeMessage assistantMessage = NarrativeMessage.builder()
                        .sessionId(sessionId)
                        .role("assistant")
                        .user(context.user()) // ✅ Context에서 User 객체 사용
                        .build();

                // 2-4. Stream API 호출 (토큰 스트림 획득)
                Flux<String> llmTokenStream = callStreamApi(jobId)
                        .doOnNext(token -> {
                            llmResponseContent.append(token);
                            log.debug("Token received: {}", token);
                        })
                        // 2-5. Stream 완료 시 최종 Status API 호출 및 DB 저장
                        .doOnComplete(() -> {
                            // 블로킹 작업을 백그라운드 스레드에서 실행
                            Mono.fromRunnable(() -> {
                                try {
                                    // Status API 동기 호출
                                    LlmDto.StatusResponse statusResponse = callStatusApi(jobId)
                                            .block();

                                    if (statusResponse != null) {
                                        // 최종 메시지를 DB에 저장
                                        assistantMessage.setContent(statusResponse.characterMessage());
                                        assistantMessage.setDescription(statusResponse.narrative());
                                        messageRepository.save(assistantMessage);
                                        log.info("AI message saved. SessionId: {}, JobId: {}", sessionId, jobId);
                                    } else {
                                        log.error("Status response is null. JobId: {}", jobId);
                                    }
                                } catch (Exception e) {
                                    log.error("Failed to save AI message. JobId: {}, Error: {}", jobId, e.getMessage(), e);
                                }
                            }).subscribeOn(Schedulers.boundedElastic()).subscribe();
                        })
                        // 2-6. 에러 처리
                        .doOnError(error -> {
                            log.error("Stream error occurred. JobId: {}, Error: {}", jobId, error.getMessage(), error);
                            // 에러 발생 시에도 부분 메시지 저장 (선택사항)
                            Mono.fromRunnable(() -> {
                                try {
                                    assistantMessage.setContent("[Error: " + error.getMessage() + "]");
                                    assistantMessage.setDescription("Stream interrupted");
                                    messageRepository.save(assistantMessage);
                                } catch (Exception e) {
                                    log.error("Failed to save error message", e);
                                }
                            }).subscribeOn(Schedulers.boundedElastic()).subscribe();
                        });

                return llmTokenStream;
            });
        });
    }

    /**
     * 이미지 콜백 상태 조회
     */
    @Transactional(readOnly = true)
    public NarrativeDto.ImageCallbackResponse getImageCallback(UUID callbackId) {
        ImageGenerationCallback callback = callbackRepository.findById(callbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Callback not found: " + callbackId));

        return NarrativeDto.ImageCallbackResponse.builder()
                .status(callback.getStatus())
                .imageUrl(callback.getImageUrl())
                .error(callback.getError())
                .build();
    }

    /**
     * TTS 생성
     */
    @Transactional
    public NarrativeDto.TtsResponse generateTts(
            Long userId,
            String sessionId,
            NarrativeDto.TtsRequest request
    ) {
        // 1. 메시지 조회
        UUID messageId = UUID.fromString(request.messageId());
        NarrativeMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found: " + messageId));

        // 2. 세션 검증
        if (!message.getSessionId().equals(sessionId)) {
            throw new ResourceNotFoundException("Message does not belong to this session");
        }

        // 3. TTS 생성 (현재는 Mock, 향후 실제 TTS API 연동)
        String audioUrl = generateTtsAudio(request.text(), request.voiceType());

        // 4. 메시지에 audio_url 업데이트
        message.setAudioUrl(audioUrl);
        messageRepository.save(message);

        return NarrativeDto.TtsResponse.builder()
                .audioUrl(audioUrl)
                .messageId(request.messageId())
                .build();
    }

    /**
     * 채팅 히스토리 조회
     */
    @Transactional(readOnly = true)
    public NarrativeDto.HistoryResponse getHistory(
            Long userId,
            String sessionId,
            Integer limit,
            Integer offset
    ) {
        // 1. 세션 검증
        UserNode userNode = userNodeRepository.findByUserId(userId.intValue())
                .orElseThrow(() -> new ResourceNotFoundException("UserNode not found: " + userId));

        boolean sessionExists = userNode.getPlayRelationships().stream()
                .anyMatch(play -> play.getId().equals(sessionId));

        if (!sessionExists) {
            throw new ResourceNotFoundException("Session not found: " + sessionId);
        }

        // 2. 메시지 조회 (최신순)
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<NarrativeMessage> messagePage = messageRepository.findBySessionIdOrderByCreatedAtDesc(sessionId, pageable);

        // 3. DTO 변환
        List<NarrativeDto.MessageInfo> messages = messagePage.getContent().stream()
                .map(msg -> NarrativeDto.MessageInfo.builder()
                        .id(msg.getId().toString())
                        .sessionId(msg.getSessionId())
                        .role(msg.getRole())
                        .content(msg.getContent())
                        .imageUrl(msg.getImageUrl())
                        .audioUrl(msg.getAudioUrl())
                        .createdAt(msg.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        long total = messageRepository.countBySessionId(sessionId);

        return NarrativeDto.HistoryResponse.builder()
                .messages(messages)
                .total((int) total)
                .build();
    }

    // ===== Helper Methods =====

    // Run API 호출: Job ID 획득
    private Mono<LlmDto.RunResponse> callRunApi(LlmDto.Request requestDto) {
        return llmWebClient.post()
                .uri("https://api.runpod.ai/v2/lgqj24q6hfg9sr/run")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(LlmDto.RunResponse.class)
                .timeout(Duration.ofSeconds(30))
                .doOnSuccess(response -> log.info("Run API success. JobId: {}", response.id()));
    }

    // Stream API 호출: 토큰 스트림 획득
    private Flux<String> callStreamApi(String jobId) {
        // RunPod의 Stream API가 JSON Lines를 보낸다고 가정하고 파싱 로직을 추가합니다.

        return llmWebClient.get()
                .uri("https://api.runpod.ai/v2/lgqj24q6hfg9sr/stream/{id}", jobId)
                .retrieve()
                // RunPod API가 SSE 표준 대신 JSON Lines를 보내므로 String으로 수신
                .bodyToFlux(String.class)
                .flatMap(jsonToken -> {
                    return parseAndExtractTokens(jsonToken);
                })
                .timeout(Duration.ofMinutes(5))
                .doOnComplete(() -> log.info("Stream completed for JobId: {}", jobId))
                .doOnError(e -> System.err.println("스트림 파싱 중 오류 발생: " + e.getMessage()));
    }

    /**
     * JSON 응답 문자열을 파싱하여 'stream' 배열에서 토큰만 추출합니다.
     * @param jsonString RunPod API에서 수신한 단일 JSON 응답 문자열
     * @return 추출된 토큰 문자열 Flux
     */
    private Flux<String> parseAndExtractTokens(String jsonString) {
        try {
            // 1. JSON String을 JsonNode로 파싱합니다.
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // 2. 'stream' 필드가 존재하는지 확인합니다.
            if (rootNode.has("stream") && rootNode.get("stream").isArray()) {
                JsonNode streamNode = rootNode.get("stream");

                // 3. 'stream' 배열을 순회하며 각 토큰(문자열)을 추출합니다.
                return Flux.fromIterable(streamNode)
                        .map(JsonNode::asText); // JsonNode를 String으로 변환하여 반환
            }

            // 4. (선택적) 상태 확인: 작업 완료(COMPLETED) 상태는 스트림의 끝을 나타낼 수 있습니다.
            if (rootNode.has("status") && "COMPLETED".equals(rootNode.get("status").asText())) {
                System.out.println("스트림이 COMPLETED 상태로 종료되었습니다.");
                return Flux.empty(); // 스트림 종료
            }

            // 'stream' 필드가 없거나 배열이 아닌 경우, 유효하지 않은 응답으로 간주하고 비어있는 Flux를 반환
            return Flux.empty();

        } catch (Exception e) {
            // 파싱 오류가 발생하면 에러를 출력하고 해당 토큰은 무시 (또는 에러 전달)
            System.err.println("JSON 파싱 오류: " + e.getMessage() + " (JSON: " + jsonString.substring(0, Math.min(100, jsonString.length())) + "...)");
            return Flux.empty(); // 오류 발생 시 해당 항목은 건너뜁니다.
        }
    }

    // Status API 호출: 최종 DB 저장 정보 획득
    // doOnTerminate에서 블로킹이 허용되므로 Mono<StatusResponse>를 block()으로 동기 처리합니다.
    private Mono<LlmDto.StatusResponse> callStatusApi(String jobId) {
        return llmWebClient.get()
                .uri("https://api.runpod.ai/v2/lgqj24q6hfg9sr/status/{id}", jobId)
                .retrieve()
                .bodyToMono(LlmDto.StatusResponse.class)
                .timeout(Duration.ofSeconds(30)); // Status DTO에 맞춰 수신
    }

    /**
     * 비동기로 이미지 생성
     */
    @Async
    public void generateImageAsync(UUID callbackId, String prompt) {
        try {
            log.info("Starting image generation for callback: {}", callbackId);

            // TODO: 실제 이미지 생성 API 호출 (Stable Diffusion, DALL-E 등)
            // 현재는 Mock으로 3초 대기 후 완료
            Thread.sleep(3000);

            String imageUrl = "https://api.rootale.com/images/generated-" + callbackId + ".jpg";

            // 콜백 업데이트
            ImageGenerationCallback callback = callbackRepository.findById(callbackId)
                    .orElseThrow(() -> new ResourceNotFoundException("Callback not found: " + callbackId));

            callback.setStatus("completed");
            callback.setImageUrl(imageUrl);
            callbackRepository.save(callback);

            // 메시지에 이미지 URL 업데이트
            if (callback.getMessageId() != null) {
                NarrativeMessage message = messageRepository.findById(callback.getMessageId())
                        .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
                message.setImageUrl(imageUrl);
                messageRepository.save(message);
            }

            log.info("Image generation completed for callback: {}", callbackId);

        } catch (InterruptedException e) {
            log.error("Image generation interrupted for callback: {}", callbackId, e);
            updateCallbackWithError(callbackId, "Image generation interrupted");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Image generation failed for callback: {}", callbackId, e);
            updateCallbackWithError(callbackId, "Image generation failed: " + e.getMessage());
        }
    }

    private void updateCallbackWithError(UUID callbackId, String error) {
        try {
            ImageGenerationCallback callback = callbackRepository.findById(callbackId)
                    .orElseThrow(() -> new ResourceNotFoundException("Callback not found: " + callbackId));
            callback.setStatus("failed");
            callback.setError(error);
            callbackRepository.save(callback);
        } catch (Exception e) {
            log.error("Failed to update callback with error", e);
        }
    }

    /**
     * TTS 오디오 생성 (현재는 Mock, 향후 실제 TTS API 연동)
     */
    private String generateTtsAudio(String text, String voiceType) {
        // TODO: 실제 TTS API 연동 (Google TTS, AWS Polly 등)
        String audioId = UUID.randomUUID().toString();
        return "https://api.rootale.com/audio/tts-" + audioId + ".mp3";
    }
}