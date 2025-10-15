package com.LiveToon.domain.fcm.repository;

import com.LiveToon.domain.fcm.entity.FCMToken;
import com.LiveToon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
    Optional<FCMToken> findByDeviceToken(String deviceToken);
    void deleteAllByUser(User user);
    void deleteByDeviceToken(String deviceToken);
    void deleteByTokenCheckTimeBefore(LocalDateTime tokenCheckTime);
}

