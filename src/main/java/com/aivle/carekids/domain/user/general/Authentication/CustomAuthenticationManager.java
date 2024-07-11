package com.aivle.carekids.domain.user.general.Authentication;

import com.aivle.carekids.domain.user.general.service.CustomUserDetailService;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UsersRepository usersRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetailsService userDetailsService = new CustomUserDetailService(usersRepository);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        // 비밀번호 검증
        if (!userDetails.getPassword().equals(authentication.getCredentials().toString())){
            throw new UserNotFoundException("잘못된 사용자입니다.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
