package com.LiveToon.domain.user.service;

import com.LiveToon.domain.topic.entity.Topic;
import com.LiveToon.domain.user.entity.User;
import com.LiveToon.domain.user.entity.UserTopic;
import com.LiveToon.domain.user.repository.UserTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTopicService {
    private final UserTopicRepository userTopicRepository;

    public UserTopic save(UserTopic userTopic) {
        return userTopicRepository.save(userTopic);
    }

    public void delete(User user, Topic topic) {
        userTopicRepository.deleteByUserAndTopic(user, topic);
    }
}
