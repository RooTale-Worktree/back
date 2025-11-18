package com.LiveToon.domain.user.service;

import com.LiveToon.domain.user.entity.User;
import com.LiveToon.domain.user.repository.UserRepository;
import com.LiveToon.domain.user.repository.UserTopicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
