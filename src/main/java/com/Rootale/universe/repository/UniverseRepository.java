package com.Rootale.universe.repository;

import com.Rootale.universe.entity.Universe;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniverseRepository extends Neo4jRepository<Universe, String> {
}