package com.Rootale.universe.service;

import com.Rootale.s3.S3FileService;
import com.Rootale.s3.S3Props;
import com.Rootale.universe.dto.UniverseDto;
import com.Rootale.universe.entity.Character;
import com.Rootale.universe.entity.Universe;
import com.Rootale.universe.repository.CharacterRepository;
import com.Rootale.universe.repository.UniverseCustomRepository;
import com.Rootale.universe.repository.UniverseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional(readOnly=true)
public class UniverseService {  // ⭐ @Transactional(readOnly = true) 제거

    private final UniverseRepository universeRepository;
    private final UniverseCustomRepository universeCustomRepository;
    private final CharacterRepository characterRepository;
    private final S3FileService s3FileService;
    private final S3Props s3Props;

    /**
     * 전체 세계관 목록 조회
     */
    public UniverseDto.UniverseListResponse getAllUniverses() {
        try {
            log.info("📋 Fetching all universes");
            List<Universe> universes = universeCustomRepository.findAllUniverses();  // ⭐ 변경
            log.info("✅ Found {} universes", universes.size());

            List<UniverseDto.UniverseSummary> summaries = universes.stream()
                    .map(this::toUniverseSummary)
                    .collect(Collectors.toList());

            return new UniverseDto.UniverseListResponse(summaries);
        } catch (Exception e) {
            log.error("❌ Failed to fetch universes: {}", e.getMessage(), e);
            throw new RuntimeException("세계관 목록 조회 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 특정 세계관 상세 조회
     */
    public UniverseDto.UniverseDetailResponse getUniverseById(String universeId) {
        try {
            log.info("📋 Fetching universe: {}", universeId);
            Universe universe = universeCustomRepository.findByUniverseId(universeId)  // ⭐ 변경
                    .orElseThrow(() -> new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId));

            log.info("✅ Found universe: {}", universe.getName());
            return toUniverseDetail(universe);
        } catch (Exception e) {
            log.error("❌ Failed to fetch universe {}: {}", universeId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 특정 세계관의 캐릭터 목록 조회
     */
    public UniverseDto.CharacterListResponse getCharactersByUniverseId(String universeId) {
        try {
            log.info("📋 Fetching characters for universe: {}", universeId);

            // ⭐ 존재 확인도 커스텀 메서드 사용
            if (universeCustomRepository.findByUniverseId(universeId).isEmpty()) {
                throw new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId);
            }

            List<Character> characters = characterRepository.findByUniverseId(universeId);
            log.info("✅ Found {} characters for universe {}", characters.size(), universeId);

            List<UniverseDto.CharacterSummary> summaries = characters.stream()
                    .map(this::toCharacterSummary)
                    .collect(Collectors.toList());

            return new UniverseDto.CharacterListResponse(summaries);
        } catch (Exception e) {
            log.error("❌ Failed to fetch characters for universe {}: {}", universeId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 새로운 세계관 생성
     */
    public UniverseDto.CreateUniverseResponse createUniverse(UniverseDto.CreateUniverseRequest request) {
        try {
            log.info("🆕 Creating new universe: {}", request.name());

            LocalDateTime now = LocalDateTime.now();
            Universe universe = Universe.builder()
                    .name(request.name())
                    .description(request.description())
                    .detailedDescription(request.detailedDescription())  // ⭐ 추가
                    .story(request.story())
                    .canon(request.canon())
                    .representativeImage(request.representativeImage())
                    .estimatedPlayTime(request.estimatedPlayTime())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            Universe savedUniverse = universeRepository.save(universe);
            log.info("✅ Universe created successfully with ID: {}", savedUniverse.getUniverseId());

            return toCreateUniverseResponse(savedUniverse);
        } catch (Exception e) {
            log.error("❌ Failed to create universe: {}", e.getMessage(), e);
            throw new RuntimeException("세계관 생성 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 세계관 수정
     */
    public UniverseDto.UniverseDetailResponse updateUniverse(
            String universeId,
            UniverseDto.UpdateUniverseRequest request) {
        try {
            log.info("✏️ Updating universe: {}", universeId);

            Universe universe = universeCustomRepository.findByUniverseId(universeId)
                    .orElseThrow(() -> new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId));

            // null이 아닌 필드만 업데이트
            if (request.name() != null) {
                universe.setName(request.name());
            }
            if (request.description() != null) {
                universe.setDescription(request.description());
            }
            if (request.detailedDescription() != null) {
                universe.setDetailedDescription(request.detailedDescription());
            }
            if (request.story() != null) {
                universe.setStory(request.story());
            }
            if (request.canon() != null) {
                universe.setCanon(request.canon());
            }
            if (request.representativeImage() != null) {
                universe.setRepresentativeImage(request.representativeImage());
            }
            if (request.estimatedPlayTime() != null) {
                universe.setEstimatedPlayTime(request.estimatedPlayTime());
            }

            universe.setUpdatedAt(LocalDateTime.now());
            Universe updatedUniverse = universeRepository.save(universe);

            log.info("✅ Universe updated successfully: {}", universeId);
            return toUniverseDetail(updatedUniverse);

        } catch (Exception e) {
            log.error("❌ Failed to update universe {}: {}", universeId, e.getMessage(), e);
            throw new RuntimeException("세계관 수정 실패: " + e.getMessage(), e);
        }
    }

    // ===== Mapper methods =====

    private UniverseDto.UniverseSummary toUniverseSummary(Universe universe) {
        return new UniverseDto.UniverseSummary(
                universe.getUniverseId(),
                universe.getName(),
                universe.getDescription(),
                generatePresignedUrl(universe.getRepresentativeImage()),
                universe.getEstimatedPlayTime(),
                universe.getCreatedAt(),
                universe.getUpdatedAt()
        );
    }

    private UniverseDto.UniverseDetailResponse toUniverseDetail(Universe universe) {
        return new UniverseDto.UniverseDetailResponse(
                universe.getUniverseId(),
                universe.getName(),
                universe.getDescription(),
                universe.getDetailedDescription(),
                generatePresignedUrl(universe.getRepresentativeImage()),
                universe.getEstimatedPlayTime(),
                universe.getCreatedAt(),
                universe.getUpdatedAt()
        );
    }

    private UniverseDto.CharacterSummary toCharacterSummary(Character character) {
        return new UniverseDto.CharacterSummary(
                character.getCharacterId(),
                character.getUniverseId(),
                character.getName(),
                character.getDescription(),
                character.getAvatarUrl(),
                character.getPersonality() != null
                        ? String.join(", ", character.getPersonality())
                        : ""
        );
    }

    private UniverseDto.CreateUniverseResponse toCreateUniverseResponse(Universe universe) {
        return new UniverseDto.CreateUniverseResponse(
                universe.getUniverseId(),
                universe.getName(),
                universe.getDescription(),
                universe.getDetailedDescription(),
                universe.getStory(),
                universe.getCanon(),
                generatePresignedUrl(universe.getRepresentativeImage()),
                universe.getEstimatedPlayTime(),
                universe.getCreatedAt(),
                universe.getUpdatedAt()
        );
    }

    // Custom Exception
    public static class UniverseNotFoundException extends RuntimeException {
        public UniverseNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * S3 키로부터 Presigned URL 생성
     */
    private String generatePresignedUrl(String s3Key) {
        if (s3Key == null || s3Key.isBlank()) {
            return null;
        }

        // 이미 전체 URL인 경우 그대로 반환
        if (s3Key.startsWith("http://") || s3Key.startsWith("https://")) {
            return s3Key;
        }

        try {
            Duration expiration = Duration.ofSeconds(
                    s3Props.maxPresignSeconds() != null ? s3Props.maxPresignSeconds() : 3600
            );
            URL url = s3FileService.presignGet(s3Props.bucket(), s3Key, expiration);
            return url.toString();
        } catch (Exception e) {
            log.warn("⚠️ Failed to generate presigned URL for key: {}", s3Key, e);
            return null;
        }
    }
}