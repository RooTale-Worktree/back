package com.Rootale.fcm.service;

import com.Rootale.fcm.entity.Topic;
import com.Rootale.fcm.entity.UserTopic;
import com.Rootale.fcm.repository.UserTopicRepository;
import com.Rootale.member.entity.User;
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
