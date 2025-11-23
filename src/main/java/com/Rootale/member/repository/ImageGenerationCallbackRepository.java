package com.Rootale.member.repository;

import com.Rootale.member.entity.ImageGenerationCallback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageGenerationCallbackRepository extends JpaRepository<ImageGenerationCallback, UUID> {
}