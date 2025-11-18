package com.LiveToon.domain.topic.repository;

import com.LiveToon.domain.topic.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByName(String name); //Optional 사용?
}
