package com.aivle.carekids.domain.user.general.service;

import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.exception.ExceptionCode;
import com.aivle.carekids.global.exception.GlobalExceptionHandler;
import com.aivle.carekids.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userData = userRepository.findByUsersEmail(username);

        if (userData == null){
            throw new UsernameNotFoundException("User not registered");
        }
        if (username != null){
            return new CustomUserDetail(userData);
        }


        return null;
    }
}
