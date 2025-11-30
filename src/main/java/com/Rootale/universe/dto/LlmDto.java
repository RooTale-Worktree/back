package com.Rootale.universe.dto;

import com.Rootale.member.entity.NarrativeMessage;
import com.Rootale.member.entity.User;
import com.Rootale.universe.entity.Character;
import com.Rootale.universe.entity.Universe;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

public class LlmDto {

    // ============================================
    // 1. REQUEST (요청)
    // ============================================
    @Builder
    public record Request(
            InputDataDto input
    ) {}

    // 최상위 레벨 Input 데이터
    @Builder
    public record InputDataDto(
            String message,

            @JsonProperty("user_name")
            String userName,

            PersonaDto persona,

            @JsonProperty("chat_history")
            ChatHistoryDto chatHistory,

            @JsonProperty("model_cfg")
            ModelConfigDto modelCfg,

            GenerationConfigDto gen
    ) {}

    // 페르소나
    @Builder
    public record PersonaDto(
            @JsonProperty("character_name")
            String characterName,

            String persona,
            String scenario,

            @JsonProperty("speaking_style")
            List<String> speakingStyle,

            List<String> constraints,

            @JsonProperty("example_dialogue")
            List<ExampleDialogueDto> exampleDialogue,

            MetaDto meta
    ) {}

    // 예시 대화
    @Builder
    public record ExampleDialogueDto(
            String role,
            String content
    ) {}

    // 메타 정보
    @Builder
    public record MetaDto(
            String affiliation
    ) {}

    // 대화 내역
    @Builder
    public record ChatHistoryDto(
            List<TurnDto> chatHistory
    ) {}

    @Builder
    public record TurnDto(
            String role,

            @JsonProperty("character_message")
            String characterMessage,

            String narrative // Assistant 메시지에만 포함될 수 있으므로 null 허용
    ) {}

    // 모델 설정
    @Builder
    public record ModelConfigDto(
            @JsonProperty("model_name")
            String modelName,

            @JsonProperty("tensor_parallel_size")
            Integer tensorParallelSize,

            @JsonProperty("gpu_memory_utilization")
            Double gpuMemoryUtilization
    ) {}

    // 생성 설정
    @Builder
    public record GenerationConfigDto(
            Double temperature,

            @JsonProperty("max_new_tokens")
            Integer maxNewTokens,

            List<String> stop
    ) {}

    @Builder
    public record MessageContext(
            User user,
            LlmDto.Request llmRequest
    ) {}

    // --- 👇 DTO 조립 정적 메서드 (팩토리 역할) 👇 ---

    /**
     * LLM 서버 요청을 위한 DTO를 조립합니다. (팩토리 역할)
     * 필요한 모든 데이터를 인자로 받습니다.
     */
    public static Request buildLlmRequestDto(
            @NonNull User user,
            @NonNull Universe universe,
            @NonNull Character character,
            @NonNull Page<NarrativeMessage> chatHistory, // 조회된 채팅 히스토리 리스트
            @NonNull String userMessageContent
    ) {
        // 1. Persona 구성
        PersonaDto personaDto = PersonaDto.builder()
                .characterName(character.getName())
                .persona(character.getDescription())
                .scenario(universe.getStory())
                .speakingStyle(character.getPersonality())
                .constraints(character.getConstraints())
                .exampleDialogue(character.getExampleDialogueList())
                .meta(MetaDto.builder().affiliation(character.getAffiliation()).build())
                .build();

        // 2. Chat History 구성
        List<TurnDto> turnDtos = chatHistory.stream()
                .map(narrativeMessage -> {
                    String role = narrativeMessage.getRole();
                    String narrative = role.equals("assistant") ? narrativeMessage.getDescription() : null;

                    return TurnDto.builder()
                            .role(role)
                            .characterMessage(narrativeMessage.getContent())
                            .narrative(narrative)
                            .build();
                })
                .toList();

        ChatHistoryDto chatHistoryDto = ChatHistoryDto.builder()
                .chatHistory(turnDtos)
                .build();

        // 3. Model Config & Generation Config (하드코딩)
        ModelConfigDto modelConfigDto = ModelConfigDto.builder()
                .modelName("gpt-oss-20b")
                .tensorParallelSize(1)
                .gpuMemoryUtilization(0.9)
                .build();

        GenerationConfigDto generationConfigDto = GenerationConfigDto.builder()
                .temperature(0.7)
                .maxNewTokens(1024)
                .stop(List.of("USER:", "\nUSER"))
                .build();

        // 4. LLM Request DTO 조립
        InputDataDto inputDataDto = InputDataDto.builder()
                .message(userMessageContent)
                .userName(user.getNickname())
                .persona(personaDto)
                .chatHistory(chatHistoryDto)
                .modelCfg(modelConfigDto)
                .gen(generationConfigDto)
                .build();

        return Request.builder()
                .input(inputDataDto)
                .build();
    }

    // ============================================
    // 2. RESPONSE (응답)
    // ============================================
    @Builder
    public record Response(
            String text,

            @JsonProperty("text_callback_url")
            String textCallbackUrl,

            @JsonProperty("image_callback_url")
            String imageCallbackUrl

    ) {}

    @Builder
    public record RunResponse(
            String id,

            String status
    ) {}

    @Builder
    public record StatusResponse(
            @JsonProperty("character_message")
            String characterMessage,

            String status,

            String narrative,

            @JsonProperty("image_prompt")
            String imagePrompt
    ) {}
}