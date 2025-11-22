package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Character;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends Neo4jRepository<Character, String> {
    Optional<Character> findByCharacterId(String characterId);

    @Query("""
        MATCH (c:Character {character_id: $characterId})<-[:HAS_CHARACTER]-(u:Universe {universe_id: $universeId})
        RETURN c
        """)
    Optional<Character> findByCharacterIdAndUniverseId(
            @Param("characterId") String characterId,
            @Param("universeId") String universeId
    );
}