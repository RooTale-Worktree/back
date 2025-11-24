package com.Rootale.system.service;

import com.Rootale.member.entity.Feedback;
import com.Rootale.member.entity.User;
import com.Rootale.member.enums.FeedbackCategory;
import com.Rootale.member.repository.FeedbackRepository;
import com.Rootale.member.repository.UserRepository;
import com.Rootale.system.dto.SystemDto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    /**
     * Health Check
     */
    public HealthResponse healthCheck() {
        log.info("🏥 Health check requested");
        return HealthResponse.healthy(appVersion);
    }

    /**
     * Feedback 생성
     */
    @Transactional
    public FeedbackResponse createFeedback(Long userId, CreateFeedbackRequest request) {
        log.info("📝 Creating feedback - userId: {}, category: {}", userId, request.category());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 카테고리 문자열을 Enum으로 변환
        FeedbackCategory category;
        try {
            category = FeedbackCategory.valueOf(request.category().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 카테고리입니다: " + request.category());
        }

        Feedback feedback = Feedback.builder()
                .user(user)
                .category(category)
                .title(request.title())
                .content(request.content())
                .build();

        Feedback savedFeedback = feedbackRepository.save(feedback);
        log.info("✅ Feedback created - id: {}, userId: {}", savedFeedback.getId(), userId);

        return FeedbackResponse.from(savedFeedback);
    }
}