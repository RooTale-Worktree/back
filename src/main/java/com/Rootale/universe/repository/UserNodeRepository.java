package com.Rootale.universe.repository;

import com.Rootale.universe.entity.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode, Integer> {

    @Query("MATCH (u:User {user_id: $userId})-[p:PLAY]->(universe:Universe) " +
            "RETURN u, p, universe")
    Optional<UserNode> findByIdWithPlayRelationships(@Param("userId") Integer userId);
}