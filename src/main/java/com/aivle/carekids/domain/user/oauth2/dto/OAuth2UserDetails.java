package com.aivle.carekids.domain.user.oauth2.dto;

import com.aivle.carekids.domain.user.models.Role;
import com.aivle.carekids.domain.user.models.SocialType;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class OAuth2UserDetails implements OAuth2User, UserDetails {

    private String attributeKey;
    private Map<String, Object> attributes;
    private String registrationId;
    private OAuth2UserInfo oAuth2UserInfo;

    public SocialType getSocialType() { return oAuth2UserInfo.getSocialType(); }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return oAuth2UserInfo.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() { return attributes.get(attributeKey).toString(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Role.USER.getRole()));
    }
}
