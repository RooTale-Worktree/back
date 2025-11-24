
package com.Rootale.member.repository;

import com.Rootale.member.entity.NarrativeMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NarrativeMessageRepository extends JpaRepository<NarrativeMessage, UUID> {

    Page<NarrativeMessage> findBySessionIdOrderByCreatedAtDesc(String sessionId, Pageable pageable);

    long countBySessionId(String sessionId);
}