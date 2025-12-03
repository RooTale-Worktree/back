package com.Rootale.universe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.LocalDateTime;
import java.util.List;

public class UniverseDto {

    /**
     * 세계관 생성 요청
     */
    public record CreateUniverseRequest(
            @NotBlank(message = "세계관 이름은 필수입니다")
            @Size(max = 255, message = "이름은 255자를 초과할 수 없습니다")
            String name,

            @Size(max = 2000, message = "스토리는 2000자를 초과할 수 없습니다")
            String story,

            @Size(max = 2000, message = "정전은 2000자를 초과할 수 없습니다")
            String canon,

            @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다")
            String description,

            @JsonProperty("detailed_description")
            @Size(max = 5000, message = "상세 설명은 5000자를 초과할 수 없습니다")
            String detailedDescription,

            @JsonProperty("estimated_play_time")
            Integer estimatedPlayTime,

            @JsonProperty("representative_image")
            String representativeImage,

            @JsonProperty("setting")
            String setting,  // 세계관 설정 전체 텍스트

            @JsonProperty("protagonist_name")
            String protagonistName,  // 주인공 이름 (한글)

            @JsonProperty("protagonist_desc")
            String protagonistDesc,  // 주인공 상세 설정 (성격, 사상 등)

            @JsonProperty("synopsis")
            String synopsis,  // 초기 대규모 시놉시스 (2000자 이상)

            @JsonProperty("twisted_synopsis")
            String twistedSynopsis  // 변주된 대규모 시놉시스 (2000자 이상)

    ) {}


    /**
     * 세계관 수정 요청
     */
    public record UpdateUniverseRequest(
            @Size(max = 255, message = "이름은 255자를 초과할 수 없습니다")
            String name,

            @Size(max = 2000, message = "스토리는 2000자를 초과할 수 없습니다")
            String story,

            @Size(max = 2000, message = "정전은 2000자를 초과할 수 없습니다")
            String canon,

            @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다")
            String description,

            @JsonProperty("detailed_description")
            @Size(max = 5000, message = "상세 설명은 5000자를 초과할 수 없습니다")
            String detailedDescription,

            @JsonProperty("estimated_play_time")
            Integer estimatedPlayTime,

            @JsonProperty("representative_image")
            String representativeImage,

            @JsonProperty("setting")
            String setting,  // 세계관 설정 전체 텍스트

            @JsonProperty("protagonist_name")
            String protagonistName,  // 주인공 이름 (한글)

            @JsonProperty("protagonist_desc")
            String protagonistDesc,  // 주인공 상세 설정 (성격, 사상 등)

            @JsonProperty("synopsis")
            String synopsis,  // 초기 대규모 시놉시스 (2000자 이상)

            @JsonProperty("twisted_synopsis")
            String twistedSynopsis  // 변주된 대규모 시놉시스 (2000자 이상)

    ) {}

    /**
     * 세계관 목록 응답
     */
    public record UniverseListResponse(
            List<UniverseResponse> universes
    ) {}

    /**
     * 세계관 정보
     */
    public record UniverseResponse(
            String id,
            String name,
            String story,
            String canon,
            String description,
            @JsonProperty("detailed_description") String detailedDescription,
            @JsonProperty("estimated_play_time") Integer estimatedPlayTime,
            @JsonProperty("representative_image") String representativeImage,
            String setting,
            @JsonProperty("protagonist_name") String protagonistName,
            @JsonProperty("protagonist_desc") String protagonistDesc,
            String synopsis,
            @JsonProperty("twisted_synopsis") String twistedSynopsis,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {}

    /**
     * 캐릭터 목록 응답
     */
    public record CharacterListResponse(
            List<CharacterSummary> characters
    ) {}

    /**
     * 캐릭터 요약 정보
     */
    public record CharacterSummary(
            String id,
            @JsonProperty("universe_id") String universeId,
            String name,
            String description,
            @JsonProperty("avatar_url") String avatarUrl,
            String personality
    ) {}


    /**
     * 이미지 업로드 응답
     */
    public record ImageUploadResponse(
            @JsonProperty("s3_key") String s3Key,
            @JsonProperty("presigned_url") String presignedUrl
    ) {}
}