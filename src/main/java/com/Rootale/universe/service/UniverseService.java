package com.Rootale.universe.service;

import com.Rootale.universe.dto.UniverseDto;
import com.Rootale.universe.entity.Character;
import com.Rootale.universe.entity.Universe;
import com.Rootale.universe.repository.CharacterRepository;
import com.Rootale.universe.repository.UniverseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional(readOnly=true)
public class UniverseService {  // ⭐ @Transactional(readOnly = true) 제거

    private final UniverseRepository universeRepository;
    private final CharacterRepository characterRepository;

    /**
     * 전체 세계관 목록 조회
     */
    public UniverseDto.UniverseListResponse getAllUniverses() {
        try {
            log.info("📋 Fetching all universes");
            List<Universe> universes = universeRepository.findAllUniverses();
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
            Universe universe = universeRepository.findById(universeId)
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

            if (!universeRepository.existsById(universeId)) {
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

    // ===== Mapper methods =====

    private UniverseDto.UniverseSummary toUniverseSummary(Universe universe) {
        return new UniverseDto.UniverseSummary(
                universe.getUniverseId(),
                universe.getName(),
                universe.getDescription(),
                universe.getRepresentativeImage(),
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
                universe.getRepresentativeImage(),
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

    // Custom Exception
    public static class UniverseNotFoundException extends RuntimeException {
        public UniverseNotFoundException(String message) {
            super(message);
        }
    }
}