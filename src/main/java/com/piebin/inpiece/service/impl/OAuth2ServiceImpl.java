package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.repository.AccountRepository;
import com.piebin.inpiece.security.GoogleUserDetails;
import com.piebin.inpiece.security.OAuth2UserInfo;
import com.piebin.inpiece.security.SecurityOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ServiceImpl extends DefaultOAuth2UserService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;
        if (provider.equals("google")) {
            log.info("google");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        }
        String id = provider + "_" + oAuth2UserInfo.getProviderId();
        log.info("id: {}", id);

        // Account Exists?
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent())
            return new SecurityOAuth2User(optionalAccount.get(), oAuth2User.getAttributes());
        Account account = Account.builder()
                .id(id)
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .provider(provider)
                .providerId(oAuth2UserInfo.getProviderId())
                .build();
        accountRepository.save(account);
        return new SecurityOAuth2User(account, oAuth2User.getAttributes());
    }
}
