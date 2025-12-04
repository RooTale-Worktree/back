package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Universe;
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
public class UniverseCustomRepository {

    private final Driver neo4jDriver;

    /**
     * 모든 Universe와 시작 Scene node_id 조회
     */
    public List<UniverseWithStartNode> findAllUniversesWithStartNode() {
        List<UniverseWithStartNode> results = new ArrayList<>();

        try (Session session = neo4jDriver.session()) {
            String query = """
                    MATCH (u:Universe)
                    OPTIONAL MATCH (u)-[:HAS_START]->(start:Scene)
                    RETURN u, start.node_id AS start_node_id
                    ORDER BY u.created_at DESC
                    """;

            log.debug("🔍 Executing query: {}", query);
            Result result = session.run(query);

            while (result.hasNext()) {
                Record record = result.next();

                try {
                    var universeNode = record.get("u").asNode();
                    var startNodeIdValue = record.get("start_node_id");
                    String startNodeId = startNodeIdValue.isNull() ? null : startNodeIdValue.asString();

                    Universe universe = mapNodeToUniverse(universeNode);
                    results.add(new UniverseWithStartNode(universe, startNodeId));

                    log.debug("✅ Mapped universe: id={}, name={}, startNodeId={}",
                            universe.getUniverseId(), universe.getName(), startNodeId);
                } catch (Exception e) {
                    log.error("❌ Error mapping universe record: {}", e.getMessage(), e);
                }
            }

            log.info("✅ Successfully fetched {} universes with start nodes", results.size());

        } catch (Exception e) {
            log.error("❌ Error fetching universes: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch universes from Neo4j", e);
        }

        return results;
    }

    /**
     * 특정 Universe와 시작 Scene node_id 조회
     */
    public Optional<UniverseWithStartNode> findByUniverseIdWithStartNode(String universeId) {
        try (Session session = neo4jDriver.session()) {
            String query = """
                    MATCH (u:Universe {universe_id: $universeId})
                    OPTIONAL MATCH (u)-[:HAS_START]->(start:Scene)
                    RETURN u, start.node_id AS start_node_id
                    """;

            log.debug("🔍 Executing query for universeId: {}", universeId);

            Result result = session.run(
                    query,
                    org.neo4j.driver.Values.parameters("universeId", universeId)
            );

            if (result.hasNext()) {
                Record record = result.next();

                var universeNode = record.get("u").asNode();
                var startNodeIdValue = record.get("start_node_id");
                String startNodeId = startNodeIdValue.isNull() ? null : startNodeIdValue.asString();

                Universe universe = mapNodeToUniverse(universeNode);

                log.info("✅ Found universe: id={}, name={}, startNodeId={}",
                        universe.getUniverseId(), universe.getName(), startNodeId);

                return Optional.of(new UniverseWithStartNode(universe, startNodeId));
            }

            log.warn("⚠️ Universe not found: {}", universeId);
            return Optional.empty();

        } catch (Exception e) {
            log.error("❌ Error fetching universe {}: {}", universeId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch universe from Neo4j", e);
        }
    }

    public Optional<Universe> findByUniverseId(String universeId) {
        try (Session session = neo4jDriver.session()) {
            String query = """
                    MATCH (u:Universe {universe_id: $universeId})
                    RETURN u
                    """;

            log.debug("🔍 Executing simple query for universeId: {}", universeId);

            Result result = session.run(
                    query,
                    org.neo4j.driver.Values.parameters("universeId", universeId)
            );

            if (result.hasNext()) {
                Record record = result.next();
                var universeNode = record.get("u").asNode();
                Universe universe = mapNodeToUniverse(universeNode);

                log.info("✅ Found universe: id={}, name={}",
                        universe.getUniverseId(), universe.getName());

                return Optional.of(universe);
            }

            log.warn("⚠️ Universe not found: {}", universeId);
            return Optional.empty();

        } catch (Exception e) {
            log.error("❌ Error fetching universe {}: {}", universeId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch universe from Neo4j", e);
        }
    }

    /**
     * Node를 Universe 엔티티로 매핑
     */
    private Universe mapNodeToUniverse(org.neo4j.driver.types.Node node) {
        return Universe.builder()
                .universeId(getStringValue(node, "universe_id"))
                .name(getStringValue(node, "name"))
                .story(getStringValue(node, "story"))
                .canon(getStringValue(node, "canon"))
                .description(getStringValue(node, "description"))
                .detailDescription(getStringValue(node, "detail_description"))
                .estimatedPlayTime(getIntValue(node, "estimated_play_time"))
                .representativeImage(getStringValue(node, "representative_image"))
                .setting(getStringValue(node, "setting"))
                .protagonistName(getStringValue(node, "protagonist_name"))
                .protagonistDesc(getStringValue(node, "protagonist_desc"))
                .synopsis(getStringValue(node, "synopsis"))
                .twistedSynopsis(getStringValue(node, "twisted_synopsis"))
                .createdAt(getDateTimeValue(node, "created_at"))
                .updatedAt(getDateTimeValue(node, "updated_at"))
                .build();
    }

    // ===== Helper Methods =====

    private String getStringValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            return value.isNull() ? null : value.asString();
        } catch (Exception e) {
            log.debug("Could not get string value for key '{}': {}", key, e.getMessage());
            return null;
        }
    }

    private Integer getIntValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            return value.isNull() ? null : value.asInt();
        } catch (Exception e) {
            log.debug("Could not get int value for key '{}': {}", key, e.getMessage());
            return null;
        }
    }

    private LocalDateTime getDateTimeValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            if (value.isNull()) {
                return null;
            }

            // Try LocalDateTime
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
                        log.debug("Could not parse datetime for key '{}': {}", key, e3.getMessage());
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            log.debug("Could not get datetime value for key '{}': {}", key, e.getMessage());
            return null;
        }
    }

    /**
     * Universe와 시작 노드 ID를 함께 담는 record
     */
    public record UniverseWithStartNode(
            Universe universe,
            String startNodeId
    ) {}
}