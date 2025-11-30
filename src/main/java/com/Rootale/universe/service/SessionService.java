package com.Rootale.universe.service;

import com.Rootale.universe.dto.SessionDto;
import com.Rootale.universe.repository.SessionRepository;
import com.Rootale.universe.repository.UniverseCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UniverseCustomRepository universeRepository;

    /**
     * 세션 생성 후 전체 세션 목록 반환
     */
    public SessionDto.CreateSessionResponse createSession(
            Long userId,
            SessionDto.CreateSessionRequest request) {

        // 1. Universe 존재 확인
        universeRepository.findByUniverseId(request.universeId())
                .orElseThrow(() -> new RuntimeException("Universe not found: " + request.universeId()));

        // 2. 세션 이름 생성 (기본값)
        String sessionName = "New Adventure";

        // 3. 세션 생성
        String sessionId = sessionRepository.createSession(
                userId,
                request.universeId(),
                request.characterId(),
                sessionName
        );

        log.info("✅ Session created: {} for user {}", sessionId, userId);

        // 4. 전체 세션 목록 조회하여 반환
        return getAllSessions(userId);
    }

    /**
     * 사용자의 모든 세션 조회
     */
    public SessionDto.CreateSessionResponse getAllSessions(Long userId) {
        List<Map<String, Object>> sessionsData = sessionRepository.findAllSessionsByUserId(userId);

        List<SessionDto.SessionInfo> sessions = sessionsData.stream()
                .map(data -> SessionDto.SessionInfo.builder()
                        .sessionId((String) data.get("sessionId"))
                        .sessionName((String) data.get("sessionName"))
                        .universeId((String) data.get("universeId"))
                        .representativeImage((String) data.get("representativeImage"))
                        .createdAt((String) data.get("createdAt"))
                        .updatedAt((String) data.get("updatedAt"))
                        .build())
                .collect(Collectors.toList());

        return SessionDto.CreateSessionResponse.builder()
                .sessions(sessions)
                .total(sessions.size())
                .build();
    }

    /**
     * 세션 삭제
     */
    public SessionDto.DeleteSessionResponse deleteSession(Long userId, String sessionId) {
        boolean deleted = sessionRepository.deleteSession(userId, sessionId);

        if (!deleted) {
            throw new RuntimeException("Session not found or already deleted");
        }

        return SessionDto.DeleteSessionResponse.builder()
                .message("Session deleted successfully")
                .build();
    }
}