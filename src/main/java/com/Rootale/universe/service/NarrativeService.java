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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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
     * 프론트에서 토큰 스트림만 요구할 시, 따로 추가적인 ResponseDto는 필요 없습니다.
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

        // 2. LLM API 호출 파이프라인
        return initialDbWork.flatMapMany(context -> {
            // 2-1. 집계용 객체(Aggregator) 생성
            // 스트림이 흐르는 동안 조각난 데이터를 모을 객체
            StreamAggregator aggregator = new StreamAggregator(sessionId, context.user());

            // 2-2. FastAPI (/v1/chat) 호출
            return llmWebClient.post()
                    .uri("/v1/chat") // 파이썬 서버 엔드포인트
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.TEXT_EVENT_STREAM) // SSE 수신 명시
                    .bodyValue(context.llmRequest())
                    .retrieve()
                    .bodyToFlux(String.class)
                    // 2-3. SSE 포맷 파싱 및 집계 (Side Effect)
                    .doOnNext(line -> {
                        // 실제 데이터 파싱 및 aggregator에 누적
                        parseAndAggregate(line, aggregator);
                    })
                    .filter(line -> line != null && !line.trim().isEmpty() && !line.contains("[DONE]"))
                    // 2-4. "data: " 접두사 제거 (순수 JSON만 남김). 아니면 service랑 controller에서 2번 랩핑하게 되버림
                    .map(line -> line.substring(5).trim())
                    // 2-5. 스트림 완료 시 DB 저장 (Side Effect)
                    .doOnComplete(() -> {
                        saveAggregatedMessage(aggregator);
                    })
                    // 2-6. 에러 처리
                    .doOnError(e -> {
                        log.error("Streaming error: {}", e.getMessage());
                        saveErrorMessage(aggregator, e.getMessage());
                    });
            // 참고: 리턴되는 Flux<String>은 프론트엔드로 그대로 흘러갑니다.
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

    // ===== Helper Methods & Class =====
    /**
     * 들어오는 SSE 라인("data: {...}")을 파싱하여 Aggregator에 누적
     */
    private void parseAndAggregate(String line, StreamAggregator aggregator) {
        if (line == null || !line.startsWith("data:")) {
            return; // SSE 데이터가 아니면 무시
        }

        String jsonStr = line.substring(5).trim(); // "data:" 제거
        if ("[DONE]".equals(jsonStr)) return; // 종료 신호 무시

        try {
            JsonNode root = objectMapper.readTree(jsonStr);
            JsonNode choices = root.path("choices");
            if (choices.isMissingNode() || choices.isEmpty()) return;

            JsonNode delta = choices.get(0).path("delta");

            // 1. 텍스트 데이터 Append (이어붙이기)
            if (delta.has("character_message")) {
                aggregator.appendCharacterMessage(delta.get("character_message").asText());
            }
            if (delta.has("narrative")) {
                aggregator.appendNarrative(delta.get("narrative").asText());
            }

            // 2. 메타데이터 Update (덮어쓰기/설정) - 시간 차가 있으나 주로 마지막 청크에 올 것임
            if (delta.has("image_prompt")) {
                aggregator.setImagePrompt(delta.get("image_prompt").asText());
            }
            if (delta.has("usage")) {
                aggregator.setUsage(delta.get("usage")); // JsonNode 그대로 저장하거나 DTO 매핑
            }
            if (delta.has("timing")) {
                aggregator.setTiming(delta.get("timing"));
            }
            /**
             * ToDo: next_state_description 받기
             * 그런데 매 대화마다 다음 스토리 노드로 넘어가는 건 아니라서 이 부분은 추후 얘기 나눠봐야할 듯
             */

        } catch (Exception e) {
            log.warn("JSON Parse Error for line: {}", line, e);
        }
    }

    /**
     * 스트림 종료 후 모아진 데이터를 DB에 저장
     */
    private void saveAggregatedMessage(StreamAggregator aggregator) {
        Mono.fromRunnable(() -> {
            try {
                // 완성된 텍스트와 메타데이터로 엔티티 생성
                NarrativeMessage aiMessage = NarrativeMessage.builder()
                        .sessionId(aggregator.getSessionId())
                        .role("assistant")
                        .user(aggregator.getUser())
                        .content(aggregator.getFullCharacterMessage()) // 완성된 대사
                        .description(aggregator.getFullNarrative())    // 완성된 서사
                        // .meta(aggregator.getUsage().toString()) // 필요시 메타데이터 저장
                        .build();

                messageRepository.save(aiMessage);
                log.info("AI Message saved successfully. SessionId: {}", aggregator.getSessionId());
            } catch (Exception e) {
                log.error("Failed to save AI message", e);
            }
        }).subscribeOn(Schedulers.boundedElastic()).subscribe(); // 비동기 실행 (Fire & Forget)
    }

    private void saveErrorMessage(StreamAggregator aggregator, String errorMsg) {
        Mono.fromRunnable(() -> {
            NarrativeMessage errorMessage = NarrativeMessage.builder()
                    .sessionId(aggregator.getSessionId())
                    .role("assistant")
                    .user(aggregator.getUser())
                    .content("[Error]")
                    .description("Error occurred during generation: " + errorMsg)
                    .build();
            messageRepository.save(errorMessage);
        }).subscribeOn(Schedulers.boundedElastic()).subscribe();
    }

    // =================================================================
    // Inner Class: Aggregator (상태 관리용)
    // =================================================================
    @Getter
    @Setter
    @RequiredArgsConstructor
    @ToString // 디버깅용
    private static class StreamAggregator {
        private final String sessionId;
        private final User user;

        private StringBuilder characterMessageBuilder = new StringBuilder();
        private StringBuilder narrativeBuilder = new StringBuilder();

        private String imagePrompt;
        private JsonNode usage;
        private JsonNode timing;

        public void appendCharacterMessage(String text) {
            if (text != null) characterMessageBuilder.append(text);
        }

        public void appendNarrative(String text) {
            if (text != null) narrativeBuilder.append(text);
        }

        public String getFullCharacterMessage() {
            return characterMessageBuilder.toString();
        }

        public String getFullNarrative() {
            return narrativeBuilder.toString();
        }
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