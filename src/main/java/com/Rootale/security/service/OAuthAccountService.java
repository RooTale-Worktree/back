package com.Rootale.security.service;

import com.Rootale.member.entity.OAuthAccount;
import com.Rootale.member.entity.User;
import com.Rootale.member.repository.OAuthAccountRepository;
import com.Rootale.member.repository.UserRepository;
import com.Rootale.security.OAuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthAccountService {

    private final OAuthAccountRepository oauthRepo;
    private final UserRepository userRepo;

    @Transactional
    public User linkOrCreateUser(OAuthDto.OAuthUserInfo info) {
        log.info("🔵 linkOrCreateUser called - provider: {}, providerId: {}, email: {}",
                info.provider(), info.providerUserId(), info.email());

        Optional<OAuthAccount> existing = oauthRepo.findByProviderAndProviderUserId(
                info.provider(), info.providerUserId());
        if (existing.isPresent()) {
            log.info("✅ Found existing OAuth account - userId: {}", existing.get().getUser().getUsersId());
            return existing.get().getUser();
        }

        if (info.email() != null) {
            Optional<User> byEmailAsUsername = userRepo.findByName(info.email());
            if (byEmailAsUsername.isPresent()) {
                log.info("✅ Found user by email - userId: {}, linking OAuth account",
                        byEmailAsUsername.get().getUsersId());
                return createLink(byEmailAsUsername.get(), info);
            }

            Optional<OAuthAccount> byProvEmail = oauthRepo.findByProviderAndEmail(
                    info.provider(), info.email());
            if (byProvEmail.isPresent()) {
                log.info("✅ Found OAuth account by provider+email - userId: {}",
                        byProvEmail.get().getUser().getUsersId());
                return byProvEmail.get().getUser();
            }
        }

        log.info("🆕 Creating new user and OAuth account");
        User user = User.builder()
                .name(info.email() != null ? info.email() : info.provider() + "_" + info.providerUserId())
                .build();

        User savedUser = userRepo.save(user);
        log.info("✅ User created - userId: {}, username: {}", savedUser.getUsersId(), savedUser.getUsername());

        return createLink(savedUser, info);
    }

    private User createLink(User user, OAuthDto.OAuthUserInfo info) {
        log.info("🔗 Creating OAuth link - userId: {}, provider: {}, providerId: {}",
                user.getUsersId(), info.provider(), info.providerUserId());

        OAuthAccount acc = OAuthAccount.builder()
                .user(user)
                .provider(info.provider())
                .providerUserId(info.providerUserId())
                .email(info.email())
                .pictureUrl(info.picture())
                .build();

        OAuthAccount saved = oauthRepo.save(acc);
        log.info("✅ OAuth account created - id: {}, provider: {}, email: {}",
                saved.getId(), saved.getProvider(), saved.getEmail());

        return user;
    }
}
