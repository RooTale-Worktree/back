package com.Rootale.fcm.service;

import com.Rootale.fcm.entity.Topic;
import com.Rootale.fcm.repository.TopicRepository;
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
