package com.Rootale.universe.service;

import com.Rootale.member.entity.ImageGenerationCallback;
import com.Rootale.member.entity.NarrativeMessage;
import com.Rootale.member.entity.User;
import com.Rootale.member.repository.ImageGenerationCallbackRepository;
import com.Rootale.member.repository.NarrativeMessageRepository;
import com.Rootale.member.repository.UserRepository;
import com.Rootale.universe.dto.NarrativeDto;
import com.Rootale.universe.entity.PlayRelationship;
import com.Rootale.universe.entity.UserNode;
import com.Rootale.universe.exception.ResourceNotFoundException;
import com.Rootale.universe.repository.UserNodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserRepository userRepository;

    @Value("${app.base-url:https://api.rootale.com}")
    private String baseUrl;

    /**
     * 유저 메시지 전송 및 AI 응답 생성
     */
    @Transactional
    public NarrativeDto.SendMessageResponse sendMessage(
            Long userId,
            String sessionId,
            NarrativeDto.SendMessageRequest request
    ) {
        // 1. 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        // 2. 세션 검증 (Neo4j)
        UserNode userNode = userNodeRepository.findByUserId(userId.intValue())
                .orElseThrow(() -> new ResourceNotFoundException("UserNode not found: " + userId));

        PlayRelationship session = userNode.getPlayRelationships().stream()
                .filter(play -> play.getId().equals(sessionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Session not found: " + sessionId));

        // 3. 유저 메시지 저장
        NarrativeMessage userMessage = NarrativeMessage.builder()
                .sessionId(sessionId)
                .role("user")
                .content(request.message())
                .user(user)
                .build();
        messageRepository.save(userMessage);

        // 4. AI 응답 생성 (현재는 단순 텍스트, 향후 Gemini API 연동)
        String aiResponseText = generateAiResponse(session, request.message());

        // 5. AI 응답 메시지 저장
        NarrativeMessage assistantMessage = NarrativeMessage.builder()
                .sessionId(sessionId)
                .role("assistant")
                .content(aiResponseText)
                .user(user)
                .build();
        messageRepository.save(assistantMessage);

        // 6. 이미지 생성 콜백 생성 (비동기)
        ImageGenerationCallback callback = ImageGenerationCallback.builder()
                .sessionId(sessionId)
                .messageId(assistantMessage.getId())
                .status("pending")
                .user(user)
                .build();
        callbackRepository.save(callback);

        // 7. 비동기로 이미지 생성 시작
        generateImageAsync(callback.getCallbackId(), aiResponseText);

        // 8. 응답 반환
        return NarrativeDto.SendMessageResponse.builder()
                .text(aiResponseText)
                .textCallbackUrl(null)  // 텍스트는 즉시 반환
                .imageCallbackUrl(baseUrl + "/narrative/callback/image/" + callback.getCallbackId())
                .build();
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

    /**
     * AI 응답 생성 (현재는 Mock, 향후 Gemini API 연동)
     */
    private String generateAiResponse(PlayRelationship session, String userMessage) {
        // TODO: Gemini API 연동
        // 1. 세션 컨텍스트 수집 (캐릭터, 현재 노드, 방문한 노드)
        // 2. 벡터 DB에서 장기기억 검색
        // 3. Gemini API 호출
        // 4. 응답 생성

        return "안녕하세요! 저는 " + session.getUniverse().getName() + "의 안내자입니다. " +
                "당신의 메시지: \"" + userMessage + "\"를 잘 받았습니다.";
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