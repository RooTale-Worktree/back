
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UniverseService {

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
            List<UniverseCustomRepository.UniverseWithStartNode> universesWithStart =
                    universeCustomRepository.findAllUniversesWithStartNode();
            log.info("✅ Found {} universes", universesWithStart.size());

            List<UniverseDto.UniverseResponseSimple> responses = universesWithStart.stream()
                    .map(uws -> toUniverseResponseSimple(uws.universe(), uws.startNodeId()))
                    .collect(Collectors.toList());

            return new UniverseDto.UniverseListResponse(responses);
        } catch (Exception e) {
            log.error("❌ Failed to fetch universes: {}", e.getMessage(), e);
            throw new RuntimeException("세계관 목록 조회 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 특정 세계관 상세 조회
     */
    public UniverseDto.UniverseResponse getUniverseById(String universeId) {
        try {
            log.info("📋 Fetching universe: {}", universeId);
            var universeWithStart = universeCustomRepository.findByUniverseIdWithStartNode(universeId)
                    .orElseThrow(() -> new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId));

            log.info("✅ Found universe: {}", universeWithStart.universe().getName());
            return toUniverseResponse(universeWithStart.universe(), universeWithStart.startNodeId());
        } catch (Exception e) {
            log.error("❌ Failed to fetch universe {}: {}", universeId, e.getMessage(), e);
            throw e;
        }
    }

    public UniverseDto.UniverseResponseSimple getUniverseByIdSimple(String universeId) {
        try {
            log.info("📋 Fetching universe: {}", universeId);
            var universeWithStart = universeCustomRepository.findByUniverseIdWithStartNode(universeId)
                    .orElseThrow(() -> new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId));

            log.info("✅ Found universe: {}", universeWithStart.universe().getName());
            return toUniverseResponseSimple(universeWithStart.universe(), universeWithStart.startNodeId());
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

            // 존재 확인
            if (universeCustomRepository.findByUniverseIdWithStartNode(universeId).isEmpty()) {
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
    public UniverseDto.UniverseResponse createUniverse(UniverseDto.CreateUniverseRequest request) {
        try {
            log.info("🆕 Creating new universe: {}", request.name());

            LocalDateTime now = LocalDateTime.now();
            Universe universe = Universe.builder()
                    .name(request.name())
                    .description(request.description())
                    .detailDescription(request.detailDescription())
                    .story(request.story())
                    .canon(request.canon())
                    .representativeImage(request.representativeImage())
                    .estimatedPlayTime(request.estimatedPlayTime())
                    .setting(request.setting())
                    .protagonistName(request.protagonistName())
                    .protagonistDesc(request.protagonistDesc())
                    .synopsis(request.synopsis())
                    .twistedSynopsis(request.twistedSynopsis())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            Universe savedUniverse = universeRepository.save(universe);
            log.info("✅ Universe created successfully with ID: {}", savedUniverse.getUniverseId());

            // 저장 후 start_node_id 조회를 위해 다시 조회
            var universeWithStart = universeCustomRepository.findByUniverseIdWithStartNode(savedUniverse.getUniverseId())
                    .orElse(new UniverseCustomRepository.UniverseWithStartNode(savedUniverse, null));

            return toUniverseResponse(universeWithStart.universe(), universeWithStart.startNodeId());
        } catch (Exception e) {
            log.error("❌ Failed to create universe: {}", e.getMessage(), e);
            throw new RuntimeException("세계관 생성 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 세계관 수정
     */
    public UniverseDto.UniverseResponse updateUniverse(String universeId, UniverseDto.UpdateUniverseRequest request) {
        try {
            log.info("✏️ Updating universe: {}", universeId);

            var universeWithStart = universeCustomRepository.findByUniverseIdWithStartNode(universeId)
                    .orElseThrow(() -> new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId));

            Universe universe = universeWithStart.universe();

            // null이 아닌 필드만 업데이트
            if (request.name() != null) {
                universe.setName(request.name());
            }
            if (request.description() != null) {
                universe.setDescription(request.description());
            }
            if (request.detailDescription() != null) {
                universe.setDetailDescription(request.detailDescription());
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
            if (request.setting() != null) {
                universe.setSetting(request.setting());
            }
            if (request.protagonistName() != null) {
                universe.setProtagonistName(request.protagonistName());
            }
            if (request.protagonistDesc() != null) {
                universe.setProtagonistDesc(request.protagonistDesc());
            }
            if (request.synopsis() != null) {
                universe.setSynopsis(request.synopsis());
            }
            if (request.twistedSynopsis() != null) {
                universe.setTwistedSynopsis(request.twistedSynopsis());
            }

            universe.setUpdatedAt(LocalDateTime.now());
            Universe updatedUniverse = universeRepository.save(universe);

            log.info("✅ Universe updated successfully: {}", universeId);

            // 업데이트 후 start_node_id 유지
            return toUniverseResponse(updatedUniverse, universeWithStart.startNodeId());

        } catch (Exception e) {
            log.error("❌ Failed to update universe {}: {}", universeId, e.getMessage(), e);
            throw new RuntimeException("세계관 수정 실패: " + e.getMessage(), e);
        }
    }

    // ===== Mapper methods =====

    private UniverseDto.UniverseResponse toUniverseResponse(Universe universe, String startNodeId) {
        return new UniverseDto.UniverseResponse(
                universe.getUniverseId(),
                universe.getName(),
                universe.getStory(),
                universe.getCanon(),
                universe.getDescription(),
                universe.getDetailDescription(),
                universe.getEstimatedPlayTime(),
                generatePresignedUrl(universe.getRepresentativeImage()),
                universe.getSetting(),
                universe.getProtagonistName(),
                universe.getProtagonistDesc(),
                universe.getSynopsis(),
                universe.getTwistedSynopsis(),
                startNodeId,
                universe.getCreatedAt(),
                universe.getUpdatedAt()
        );
    }

    private UniverseDto.UniverseResponseSimple toUniverseResponseSimple(Universe universe, String startNodeId) {
        return new UniverseDto.UniverseResponseSimple(
                universe.getUniverseId(),
                universe.getName(),
                universe.getStory(),
                universe.getCanon(),
                universe.getDescription(),
                universe.getDetailDescription(),
                universe.getEstimatedPlayTime(),
                generatePresignedUrl(universe.getRepresentativeImage()),
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

    // Custom Exception
    public static class UniverseNotFoundException extends RuntimeException {
        public UniverseNotFoundException(String message) {
            super(message);
        }
    }
}