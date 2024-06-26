package com.aivle.carekids.domain.user.oauth2.dto;

import com.aivle.carekids.domain.user.models.SocialType;
import jakarta.security.auth.message.AuthException;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class OAuth2UserInfo {

    private String email;
    private SocialType socialType;

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) throws AuthException {

        switch (SocialType.valueOf(registrationId)) {
            case NAVER -> {
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                return OAuth2UserInfo.builder()
                        .email((String) response.get("email"))
                        .socialType(SocialType.NAVER)
                        .build();
            }
            case GOOGLE -> {
                return OAuth2UserInfo.builder()
                        .email((String) attributes.get("email"))
                        .socialType(SocialType.GOOGLE)
                        .build();
            }
            default -> {
                throw new AuthException("잘못된 접근 입니다.");
            }
        }
    }
}
