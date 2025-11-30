package com.Rootale.universe.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final Driver neo4jDriver;
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * 세션 생성 (User-[PLAY]->Universe)
     */
    public String createSession(Long userId, String universeId, String characterId, String sessionName) {
        try (Session session = neo4jDriver.session()) {
            String sessionId = UUID.randomUUID().toString();

            Result result = session.run("""
                MATCH (user:User {user_id: $userId})
                MATCH (universe:Universe {universe_id: $universeId})
                OPTIONAL MATCH (universe)-[:HAS_START]->(startScene:Scene)
                CREATE (user)-[p:PLAY {
                    session_id: $sessionId,
                    session_name: $sessionName,
                    character_id: $characterId,
                    last_read_node_id: startScene.nodeId,
                    progress: 0.0,
                    created_at: datetime(),
                    updated_at: datetime()
                }]->(universe)
                RETURN p.session_id as sessionId
                """,
                    Map.of(
                            "userId", userId,
                            "universeId", universeId,
                            "characterId", characterId,
                            "sessionId", sessionId,
                            "sessionName", sessionName
                    )
            );

            if (result.hasNext()) {
                return result.next().get("sessionId").asString();
            }

            throw new RuntimeException("Failed to create session");
        } catch (Exception e) {
            log.error("❌ Error creating session: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create session", e);
        }
    }

    /**
     * 사용자의 모든 세션 조회
     */
    public List<Map<String, Object>> findAllSessionsByUserId(Long userId) {
        try (Session session = neo4jDriver.session()) {
            Result result = session.run("""
                MATCH (user:User {user_id: $userId})-[p:PLAY]->(universe:Universe)
                RETURN p.session_id as sessionId,
                       p.session_name as sessionName,
                       universe.universe_id as universeId,
                       universe.representative_image as representativeImage,
                       p.created_at as createdAt,
                       p.updated_at as updatedAt
                ORDER BY p.updated_at DESC
                """,
                    Map.of("userId", userId)
            );

            List<Map<String, Object>> sessions = new ArrayList<>();
            while (result.hasNext()) {
                Record record = result.next();

                Map<String, Object> sessionMap = new HashMap<>();
                sessionMap.put("sessionId", record.get("sessionId").asString());
                sessionMap.put("sessionName", record.get("sessionName").asString(""));
                sessionMap.put("universeId", record.get("universeId").asString());
                sessionMap.put("representativeImage", record.get("representativeImage").asString(""));
                sessionMap.put("createdAt", formatDateTime(record.get("createdAt")));
                sessionMap.put("updatedAt", formatDateTime(record.get("updatedAt")));

                sessions.add(sessionMap);
            }

            return sessions;
        } catch (Exception e) {
            log.error("❌ Error fetching sessions: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch sessions", e);
        }
    }

    /**
     * 특정 세션 조회
     */
    public Optional<Map<String, Object>> findSessionById(Integer userId, String sessionId) {
        try (Session session = neo4jDriver.session()) {
            Result result = session.run("""
                MATCH (user:User {user_id: $userId})-[p:PLAY]->(universe:Universe)
                WHERE p.session_id = $sessionId
                RETURN p.session_id as sessionId,
                       p.session_name as sessionName,
                       universe.universe_id as universeId,
                       universe.representative_image as representativeImage,
                       p.created_at as createdAt,
                       p.updated_at as updatedAt
                """,
                    Map.of("userId", userId, "sessionId", sessionId)
            );

            if (result.hasNext()) {
                Record record = result.next();

                Map<String, Object> sessionMap = new HashMap<>();
                sessionMap.put("sessionId", record.get("sessionId").asString());
                sessionMap.put("sessionName", record.get("sessionName").asString(""));
                sessionMap.put("universeId", record.get("universeId").asString());
                sessionMap.put("representativeImage", record.get("representativeImage").asString(""));
                sessionMap.put("createdAt", formatDateTime(record.get("createdAt")));
                sessionMap.put("updatedAt", formatDateTime(record.get("updatedAt")));

                return Optional.of(sessionMap);
            }

            return Optional.empty();
        } catch (Exception e) {
            log.error("❌ Error fetching session: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * 세션 삭제
     */
    public boolean deleteSession(Long userId, String sessionId) {
        try (Session session = neo4jDriver.session()) {
            Result result = session.run("""
                MATCH (user:User {user_id: $userId})-[p:PLAY]->(universe:Universe)
                WHERE p.session_id = $sessionId
                DELETE p
                RETURN count(p) > 0 as deleted
                """,
                    Map.of("userId", userId, "sessionId", sessionId)
            );

            if (result.hasNext()) {
                return result.next().get("deleted").asBoolean();
            }

            return false;
        } catch (Exception e) {
            log.error("❌ Error deleting session: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * DateTime 포맷팅 (ISO 8601)
     */
    private String formatDateTime(org.neo4j.driver.Value value) {
        try {
            if (value.isNull()) return null;

            // ZonedDateTime으로 변환 시도
            ZonedDateTime zdt = value.asZonedDateTime();
            return zdt.format(ISO_FORMATTER);
        } catch (Exception e) {
            log.debug("Could not format datetime: {}", e.getMessage());
            return null;
        }
    }
}
