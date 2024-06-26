package com.aivle.carekids.domain.user.oauth2.service;

import com.aivle.carekids.domain.user.oauth2.dto.OAuth2UserDetails;
import com.aivle.carekids.domain.user.oauth2.dto.OAuth2UserInfo;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. 유저 정보(attributes) 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        // 2. resistrationId 가져오기 (third-party id) ex. google, naver, ...
        String registrationId = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        // 3. userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 4. 유저 정보 dto 생성
        OAuth2UserInfo oAuth2UserInfo;
        try { oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes); }
        catch (AuthException e) { throw new RuntimeException(e); }

        //5. OAuth2User로 반환
        return new OAuth2UserDetails(userNameAttributeName, oAuth2UserAttributes, registrationId, oAuth2UserInfo);
    }
}
