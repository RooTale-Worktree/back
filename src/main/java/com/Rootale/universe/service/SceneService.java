package com.Rootale.universe.service;

import com.Rootale.universe.dto.ChoiceRelationshipDto;
import com.Rootale.universe.dto.SceneWithCandidatesDto;
import com.Rootale.universe.repository.SceneCustomRepository;
import com.Rootale.universe.repository.SceneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SceneService {

    private final SceneRepository sceneRepository;
    private final SceneCustomRepository sceneCustomRepository;

    public SceneWithCandidatesDto getSceneWithCandidates(String nodeId) {
        log.info("📋 Fetching scene with candidates: {}", nodeId);

        var result = sceneCustomRepository.findSceneWithCandidates(nodeId)
                .orElseThrow(() -> new SceneNotFoundException("Scene not found with nodeId: " + nodeId));

        // Relationship 정보를 DTO로 변환
        var choiceRelations = result.relations().stream()
                .map(rel -> ChoiceRelationshipDto.builder()
                        .id(rel.id())
                        .choiceText(rel.choiceText())
                        .targetNodeId(rel.targetNodeId())
                        .build())
                .collect(Collectors.toList());

        return SceneWithCandidatesDto.builder()
                .current(result.current())
                .candidateScenes(result.candidates())
                .candidateCount(result.candidates().size())
                .choiceRelations(choiceRelations)
                .build();
    }

    public static class SceneNotFoundException extends RuntimeException {
        public SceneNotFoundException(String message) {
            super(message);
        }
    }
}