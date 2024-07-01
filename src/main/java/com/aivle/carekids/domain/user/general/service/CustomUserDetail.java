package com.aivle.carekids.domain.user.general.service;


import com.aivle.carekids.domain.user.models.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {
    private final Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority(){
            @Override
            public String getAuthority(){
                return users.getUsersRole().getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return users.getUsersPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsersEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
