package com.Rootale.universe.service;

import com.Rootale.universe.dto.UniverseDto;
import com.Rootale.universe.entity.Character;
import com.Rootale.universe.entity.Universe;
import com.Rootale.universe.repository.CharacterRepository;
import com.Rootale.universe.repository.UniverseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniverseService {

    private final UniverseRepository universeRepository;
    private final CharacterRepository characterRepository;

    /**
     * 전체 세계관 목록 조회
     */
    public UniverseDto.UniverseListResponse getAllUniverses() {
        List<Universe> universes = universeRepository.findAll();
        List<UniverseDto.UniverseSummary> summaries = universes.stream()
                .map(this::toUniverseSummary)
                .collect(Collectors.toList());
        return new UniverseDto.UniverseListResponse(summaries);
    }

    /**
     * 특정 세계관 상세 조회
     */
    public UniverseDto.UniverseDetailResponse getUniverseById(String universeId) {
        Universe universe = universeRepository.findById(universeId)
                .orElseThrow(() -> new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId));
        return toUniverseDetail(universe);
    }

    /**
     * 특정 세계관의 캐릭터 목록 조회
     */
    public UniverseDto.CharacterListResponse getCharactersByUniverseId(String universeId) {
        // 세계관 존재 여부 확인
        if (!universeRepository.existsById(universeId)) {
            throw new UniverseNotFoundException("세계관을 찾을 수 없습니다: " + universeId);
        }

        List<Character> characters = characterRepository.findByUniverseId(universeId);
        List<UniverseDto.CharacterSummary> summaries = characters.stream()
                .map(this::toCharacterSummary)
                .collect(Collectors.toList());
        return new UniverseDto.CharacterListResponse(summaries);
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