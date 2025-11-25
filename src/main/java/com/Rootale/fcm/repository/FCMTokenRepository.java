package com.Rootale.fcm.repository;

import com.Rootale.fcm.entity.FcmToken;
import com.Rootale.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FCMTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByDeviceToken(String deviceToken);
    void deleteAllByUser(User user);
    FcmToken deleteAllByDeviceToken(String deviceToken);
    List<FcmToken> deleteByDeviceTokenIn(List<String> deviceToken);
    void deleteByTokenCheckTimeBefore(LocalDateTime tokenCheckTime);

    @Query("SELECT f.deviceToken " +
            "FROM FcmToken f " +
            "WHERE f.user.usersId = :userId " +    // 1. 유저 ID로 필터링
            "AND f.isActive = true " +         // 2. 유효한 토큰만
            "AND f.notificationEnabled = true") // 3. 알림 활성화된 토큰만
    List<String> findValidAndEnabledDeviceTokensByUserId(@Param("userId") Long userId);

    @Query("SELECT ft.deviceToken " +
            "FROM FcmToken ft " +
            "JOIN ft.user u " +                 // 1. fcmToken -> user 조인
            "JOIN UserTopic ut ON u.usersId = ut.user.usersId " + // 2. user -> userTopic 조인
            "WHERE ut.topic.name = :topic " +    // 3. 찾으려는 토픽
            "  AND ut.isActive = true " +        // 4. 토픽 구독이 활성화된
            "  AND ft.notificationEnabled = true") // 5. 해당 기기 알림이 켜진
    List<String> findActiveDeviceTokensByTopic(@Param("topic") String topic);
}

