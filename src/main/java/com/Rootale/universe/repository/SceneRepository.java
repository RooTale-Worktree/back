package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Scene;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneRepository extends Neo4jRepository<Scene, String> {
    @Query("""
        MATCH (current:Scene {node_id: $nodeId})
        OPTIONAL MATCH (current)-[prereq:PREREQUISITION]->(candidate:Scene)
        RETURN current,
        collect(DISTINCT candidate) AS candidateScenes,
        collect(DISTINCT prereq) AS prerequisitionRels
        """)
    Scene findSceneWithCandidates(String nodeId);
}