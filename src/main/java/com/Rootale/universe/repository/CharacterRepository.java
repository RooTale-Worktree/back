package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Character;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends Neo4jRepository<Character, String> {

    @Query("MATCH (u:Universe {universeId: $universeId})-[:HAS_CHARACTER]->(c:Character) RETURN c")
    List<Character> findByUniverseId(@Param("universeId") String universeId);
}