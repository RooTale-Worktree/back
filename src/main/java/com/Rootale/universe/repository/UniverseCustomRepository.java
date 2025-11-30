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

    public List<Universe> findAllUniverses() {
        List<Universe> universes = new ArrayList<>();

        try (Session session = neo4jDriver.session()) {
            Result result = session.run("MATCH (u:Universe) RETURN u");

            while (result.hasNext()) {
                Record record = result.next();
                var node = record.get("u").asNode();

                Universe universe = Universe.builder()
                        .universeId(getStringValue(node, "universe_id"))
                        .name(getStringValue(node, "name"))
                        .story(getStringValue(node, "story"))
                        .canon(getStringValue(node, "canon"))
                        .description(getStringValue(node, "description"))
                        .estimatedPlayTime(getIntValue(node, "estimated_play_time"))
                        .representativeImage(getStringValue(node, "representative_image"))
                        .createdAt(getDateTimeValue(node, "created_at"))
                        .updatedAt(getDateTimeValue(node, "updated_at"))
                        .build();

                universes.add(universe);
            }

            log.info("✅ Successfully fetched {} universes", universes.size());
        } catch (Exception e) {
            log.error("❌ Error fetching universes: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch universes from Neo4j", e);
        }

        return universes;
    }

    public Optional<Universe> findByUniverseId(String universeId) {
        try (Session session = neo4jDriver.session()) {
            Result result = session.run(
                    "MATCH (u:Universe {universe_id: $universeId}) RETURN u",
                    org.neo4j.driver.Values.parameters("universeId", universeId)
            );

            if (result.hasNext()) {
                Record record = result.next();
                var node = record.get("u").asNode();

                Universe universe = Universe.builder()
                        .universeId(getStringValue(node, "universe_id"))
                        .name(getStringValue(node, "name"))
                        .story(getStringValue(node, "story"))
                        .canon(getStringValue(node, "canon"))
                        .description(getStringValue(node, "description"))
                        .estimatedPlayTime(getIntValue(node, "estimated_play_time"))
                        .representativeImage(getStringValue(node, "representative_image"))
                        .createdAt(getDateTimeValue(node, "created_at"))
                        .updatedAt(getDateTimeValue(node, "updated_at"))
                        .build();

                return Optional.of(universe);
            }

            return Optional.empty();
        } catch (Exception e) {
            log.error("❌ Error fetching universe {}: {}", universeId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch universe from Neo4j", e);
        }
    }

    // Helper methods
    private String getStringValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            return value.isNull() ? null : value.asString();
        } catch (Exception e) {
            log.debug("Could not get string value for key: {}", key);
            return null;
        }
    }

    private Integer getIntValue(org.neo4j.driver.types.Node node, String key) {
        try {
            var value = node.get(key);
            return value.isNull() ? null : value.asInt();
        } catch (Exception e) {
            log.debug("Could not get int value for key: {}", key);
            return null;
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
}