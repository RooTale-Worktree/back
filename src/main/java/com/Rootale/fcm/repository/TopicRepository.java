package com.Rootale.fcm.repository;

import com.Rootale.fcm.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByName(String name); //Optional 사용?
}
