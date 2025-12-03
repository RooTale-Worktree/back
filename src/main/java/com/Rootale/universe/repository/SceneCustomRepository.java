
package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Scene;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SceneCustomRepository {

    private final Driver neo4jDriver;

    /**
     * Scene과 관련된 모든 관계 조회 (CHOICE, PREREQUISITION)
     */
    public Optional<SceneWithCandidatesAndRelations> findSceneWithCandidates(String nodeId) {
        log.info("🔍 Starting query for nodeId: {}", nodeId);

        try (Session session = neo4jDriver.session()) {
            String query = """
                        MATCH (current:Scene {node_id: $nodeId})
                        OPTIONAL MATCH (current)-[c:CHOICE]->(candidate:Scene)
                        RETURN current,
                               collect(DISTINCT candidate) AS candidates,
                               collect(DISTINCT {
                                  id: id(c),
                                  choice_text: c.choice_text,
                                  target_node_id: candidate.node_id
                              }) AS relations
                        """;

            log.debug("📝 Executing query with nodeId: {}", nodeId);

            Result result = session.run(query, org.neo4j.driver.Values.parameters("nodeId", nodeId));

            if (!result.hasNext()) {
                log.warn("⚠️ Scene not found with nodeId: {}", nodeId);
                return Optional.empty();
            }

            Record record = result.next();

            // Current Scene 매핑
            var currentNode = record.get("current").asNode();
            Scene current = mapNodeToScene(currentNode);
            log.info("✅ Mapped current scene: {}", current.getNodeId());

            // Candidate Scenes 매핑
            List<Scene> candidates = new ArrayList<>();
            var candidatesListValue = record.get("candidates");

            if (!candidatesListValue.isNull() && candidatesListValue.size() > 0) {
                candidates = candidatesListValue.asList(value -> {
                            if (value != null && !value.isNull()) {
                                return mapNodeToScene(value.asNode());
                            }
                            return null;
                        }).stream()
                        .filter(scene -> scene != null)
                        .toList();
            }

            // Relations 매핑
            List<RelationshipInfo> relations = new ArrayList<>();
            var relationsListValue = record.get("relations");

            log.debug("📦 Relations value type: {}, isNull: {}, size: {}",
                    relationsListValue.type(),
                    relationsListValue.isNull(),
                    relationsListValue.isNull() ? 0 : relationsListValue.size());

            if (!relationsListValue.isNull() && relationsListValue.size() > 0) {
                relations = relationsListValue.asList(value -> {
                            if (value != null && !value.isNull()) {
                                try {
                                    var map = value.asMap();
                                    Long id = map.get("id") != null ? ((Number) map.get("id")).longValue() : null;
                                    String choiceText = (String) map.get("choice_text");
                                    String targetNodeId = (String) map.get("target_node_id");

                                    // target_node_id가 null이 아닌 경우만 추가
                                    if (targetNodeId != null) {
                                        log.debug("  🔗 Mapped relation: id={}, choice_text={}, target={}",
                                                id, choiceText, targetNodeId);
                                        return new RelationshipInfo(id, choiceText, targetNodeId);
                                    }
                                } catch (Exception e) {
                                    log.warn("⚠️ Failed to map relation: {}", e.getMessage());
                                }
                            }
                            return null;
                        }).stream()
                        .filter(r -> r != null)
                        .toList();
            }

            log.info("✅ Found scene {} with {} candidates and {} relations",
                    nodeId, candidates.size(), relations.size());

            return Optional.of(new SceneWithCandidatesAndRelations(current, candidates, relations));

        } catch (org.neo4j.driver.exceptions.Neo4jException e) {
            log.error("❌ Neo4j error: {}", e.getMessage());
            log.error("   Code: {}", e.code());
            throw new RuntimeException("Neo4j query failed: " + e.getMessage(), e);

        } catch (Exception e) {
            log.error("❌ Unexpected error fetching scene {}: {}", nodeId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch scene from Neo4j: " + e.getMessage(), e);
        }
    }

    /**
     * Node를 Scene 엔티티로 매핑
     */
    private Scene mapNodeToScene(org.neo4j.driver.types.Node node) {
        return Scene.builder()
                .nodeId(getStringValue(node, "node_id"))
                .title(getStringValue(node, "title"))
                .description(getStringValue(node, "description"))
                .setting(getStringValue(node, "setting"))
                .phase(getStringValue(node, "phase"))
                .characters(getStringValue(node, "characters"))
                .characterState(getStringValue(node, "character_state"))
                .purpose(getStringValue(node, "purpose"))
                .createdAt(getDateTimeValue(node, "created_at"))
                .updatedAt(getDateTimeValue(node, "updated_at"))
                .build();
    }

    // ===== Helper methods =====

    private String getStringValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            return value.isNull() ? null : value.asString();
        } catch (Exception e) {
            log.debug("Could not get string value for key: {}", key);
            return null;
        }
    }

    /**
     * Relationship에서 속성값 가져오기
     */
    private String getStringValueFromRel(org.neo4j.driver.types.Relationship rel, String key) {
        try {
            var value = rel.get(key);
            return value.isNull() ? null : value.asString();
        } catch (Exception e) {
            log.debug("Could not get string value from relationship for key: {}", key);
            return null;
        }
    }

    /**
     * Scene, 후보 Scene 리스트, Relationship 정보를 담는 내부 클래스
     */
    public record SceneWithCandidatesAndRelations(
            Scene current,
            List<Scene> candidates,
            List<RelationshipInfo> relations
    ) {}

    /**
     * Relationship 정보를 담는 클래스
     */
    public record RelationshipInfo(
            Long id,
            String choiceText,
            String targetNodeId
    ) {}

    private Integer getIntValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            return value.isNull() ? null : value.asInt();
        } catch (Exception e) {
            log.debug("Could not get int value for key: {}", key);
            return null;
        }
    }

    private List<String> getListValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            if (value.isNull()) return new ArrayList<>();
            return value.asList(v -> v.asString());
        } catch (Exception e) {
            log.debug("Could not get list value for key: {}", key);
            return new ArrayList<>();
        }
    }

    private LocalDateTime getDateTimeValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            if (value.isNull()) return null;

            // Try LocalDateTime first
            try {
                return value.asLocalDateTime();
            } catch (Exception e1) {
                // Try ZonedDateTime
                try {
                    return value.asZonedDateTime().toLocalDateTime();
                } catch (Exception e2) {
                    // Try parsing as String (ISO format)
                    try {
                        return LocalDateTime.parse(value.asString());
                    } catch (Exception e3) {
                        log.debug("Could not parse datetime for key: {}", key);
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            log.debug("Could not get datetime value for key: {}", key);
            return null;
        }
    }

    /**
     * Scene과 후보 Scene 리스트를 담는 내부 클래스
     */
    public record SceneWithCandidates(
            Scene current,
            List<Scene> candidates
    ) {}
}