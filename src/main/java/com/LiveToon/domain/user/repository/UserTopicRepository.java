package com.LiveToon.domain.user.repository;

import com.LiveToon.domain.topic.entity.Topic;
import com.LiveToon.domain.user.entity.User;
import com.LiveToon.domain.user.entity.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {
    void deleteByUserAndTopic(User user, Topic topic);
}
