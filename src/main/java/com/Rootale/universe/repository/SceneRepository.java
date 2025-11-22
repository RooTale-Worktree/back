package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Scene;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneRepository extends Neo4jRepository<Scene, String> {
}