package com.Rootale.fcm.repository;

import com.Rootale.fcm.entity.Topic;
import com.Rootale.fcm.entity.UserTopic;
import com.Rootale.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {
    void deleteByUserAndTopic(User user, Topic topic);
}
