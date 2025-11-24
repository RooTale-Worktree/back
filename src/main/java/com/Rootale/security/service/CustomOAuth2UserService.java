package com.Rootale.security.service;

import com.Rootale.member.entity.User;
import com.Rootale.security.OAuthDto;
import com.Rootale.security.OAuthUserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuthUserInfoFactory userInfoFactory;
    private final OAuthAccountService linkService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(req);
        String regId = req.getClientRegistration().getRegistrationId();

        log.info("🔵 OAuth2 login started - provider: {}", regId);
        log.debug("OAuth2 attributes: {}", oAuth2User.getAttributes());

        OAuthDto.OAuthUserInfo info = userInfoFactory.from(regId, oAuth2User.getAttributes());
        log.info("🔵 Extracted user info - provider: {}, providerId: {}, email: {}",
                info.provider(), info.providerUserId(), info.email());

        User user = linkService.linkOrCreateUser(info);
        log.info("✅ User linked/created - userId: {}, username: {}",
                user.getUsersId(), user.getUsername());

        Collection<GrantedAuthority> auths = List.of((GrantedAuthority) () -> "ROLE_USER");
        String nameKey = req.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        return new DefaultOAuth2User(auths, oAuth2User.getAttributes(), nameKey);
    }
}