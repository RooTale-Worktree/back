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

    List<Character> findByUniverseId(String universeId);

    @Query("""
        MATCH (c:Character {character_id: $characterId})<-[:HAS_CHARACTER]-(u:Universe {universe_id: $universeId})
        RETURN c
        """)
    Optional<Character> findByCharacterIdAndUniverseId(
            @Param("characterId") String characterId,
            @Param("universeId") String universeId
    );

    @Query("""
        // 1. User와 Universe 노드를 찾습니다.
        MATCH (u:User {user_id: $userId})
        MATCH (un:Universe {universe_id: $universeId})

        // 2. User가 INTERACT 관계를 맺은 Character(c)를 찾습니다.
        MATCH (u)-[:INTERACT]->(c:Character)

        // 3. CRITICAL VALIDATION: 해당 Character(c)가 Target Universe(un)에 속해 있는지 검증합니다.
        MATCH (un)-[:HAS_CHARACTER]->(c)

        RETURN c
        """)
    Character findInteractingCharactersByUniverseId(
            @Param("userId") Long userId,
            @Param("universeId") String universeId);
}