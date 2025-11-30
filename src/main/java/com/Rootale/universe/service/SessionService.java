
package com.Rootale.universe.service;

import com.Rootale.universe.dto.SessionDto;
import com.Rootale.universe.entity.*;
import com.Rootale.universe.entity.Character;
import com.Rootale.universe.exception.ResourceNotFoundException;
import com.Rootale.universe.repository.CharacterRepository;
import com.Rootale.universe.repository.UniverseRepository;
import com.Rootale.universe.repository.UserNodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {  // ⭐ @Transactional 제거

    private final UserNodeRepository userNodeRepository;
    private final UniverseRepository universeRepository;
    private final CharacterRepository characterRepository;

    /**
     * 새로운 세션(플레이) 생성
     */
    public SessionDto.CreateSessionResponse createSession(Integer userId, SessionDto.CreateSessionRequest request) {
        try {
            log.info("🎮 Creating session for userId: {}, universeId: {}", userId, request.universeId());

            // 1. User 노드 조회 또는 생성
            UserNode userNode = userNodeRepository.findByUserId(userId)
                    .orElseGet(() -> {
                        UserNode newUser = UserNode.builder()
                                .userId(userId)
                                .playRelationships(new ArrayList<>())
                                .interactRelationships(new ArrayList<>())
                                .build();
                        return userNodeRepository.save(newUser);
                    });

            // 2. Universe 조회
            Universe universe = universeRepository.findByUniverseId(request.universeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Universe not found: " + request.universeId()));

            // 3. Character 조회
            Character character = characterRepository.findByCharacterIdAndUniverseId(
                            request.characterId(), request.universeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Character not found or not belongs to this universe: " + request.characterId()));

            // 4. PLAY 관계 생성
            PlayRelationship playRelationship = PlayRelationship.builder()
                    .universe(universe)
                    .characterId(request.characterId())
                    .sessionName(universe.getName())
                    .progress(0.0f)
                    .lastReadNodeId(universe.getStartScene() != null ? universe.getStartScene().getNodeId() : null)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            userNode.getPlayRelationships().add(playRelationship);

            // 5. INTERACT 관계 생성
            InteractRelationship interactRelationship = InteractRelationship.builder()
                    .character(character)
                    .build();

            userNode.getInteractRelationships().add(interactRelationship);

            // 6. 저장
            userNodeRepository.save(userNode);

            // 7. 첫 메시지 및 이미지 생성
            String firstMessage = generateFirstMessage(character, universe);
            String firstImage = universe.getStartScene() != null && universe.getStartScene().getNodeId() != null
                    ? generateSceneImage(universe.getStartScene().getNodeId())
                    : universe.getRepresentativeImage();

            log.info("✅ Session created successfully");
            return SessionDto.CreateSessionResponse.builder()
                    .firstMessage(firstMessage)
                    .firstImage(firstImage)
                    .build();
        } catch (Exception e) {
            log.error("❌ Failed to create session: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 사용자의 세션 목록 조회
     */
    public SessionDto.SessionListResponse getSessions(Integer userId, Integer limit, Integer offset) {
        try {
            log.info("📋 Fetching sessions for userId: {}", userId);

            UserNode userNode = userNodeRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found in Neo4j: " + userId));

            List<PlayRelationship> allSessions = userNode.getPlayRelationships();

            List<SessionDto.SessionInfo> sessionInfos = allSessions.stream()
                    .sorted((a, b) -> b.getUpdatedAt().compareTo(a.getUpdatedAt()))
                    .skip(offset)
                    .limit(limit)
                    .map(play -> SessionDto.SessionInfo.builder()
                            .sessionId(play.getId())
                            .sessionName(play.getSessionName())
                            .universeId(play.getUniverse().getUniverseId())
                            .representativeImage(play.getUniverse().getRepresentativeImage())
                            .createdAt(play.getCreatedAt().toInstant(ZoneOffset.UTC))
                            .updatedAt(play.getUpdatedAt().toInstant(ZoneOffset.UTC))
                            .build())
                    .collect(Collectors.toList());

            log.info("✅ Found {} sessions for userId: {}", sessionInfos.size(), userId);
            return SessionDto.SessionListResponse.builder()
                    .sessions(sessionInfos)
                    .total(allSessions.size())
                    .build();
        } catch (Exception e) {
            log.error("❌ Failed to fetch sessions: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 세션 삭제
     */
    public SessionDto.DeleteSessionResponse deleteSession(Integer userId, String sessionId) {
        try {
            log.info("🗑️ Deleting session: {} for userId: {}", sessionId, userId);

            boolean deleted = userNodeRepository.deletePlayRelationshipById(userId, sessionId);

            if (!deleted) {
                throw new ResourceNotFoundException("Session not found: " + sessionId);
            }

            log.info("✅ Session deleted successfully");
            return SessionDto.DeleteSessionResponse.builder()
                    .message("세션이 삭제되었습니다")
                    .build();
        } catch (Exception e) {
            log.error("❌ Failed to delete session: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ===== Helper Methods =====

    private String generateFirstMessage(Character character, Universe universe) {
        return String.format("안녕하세요! 저는 %s입니다. %s의 세계로 안내하겠습니다.",
                character.getName(),
                universe.getName());
    }

    private String generateSceneImage(String sceneId) {
        return "https://api.rootale.com/images/scene-" + sceneId + ".jpg";
    }
}