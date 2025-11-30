package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Universe;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UniverseRepository extends Neo4jRepository<Universe, String> {
    Optional<Universe> findByUniverseId(String universeId);

    // ⭐ 관계 없이 Universe 노드만 조회
    @Query("""
        MATCH (u:Universe)
        RETURN u
        """)
    List<Universe> findAllUniverses();
}