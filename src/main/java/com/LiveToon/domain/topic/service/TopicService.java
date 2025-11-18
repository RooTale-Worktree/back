package com.LiveToon.domain.topic.service;

import com.LiveToon.domain.topic.entity.Topic;
import com.LiveToon.domain.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public Topic findByName(String name) {
        return topicRepository.findByName(name);
    }
}
