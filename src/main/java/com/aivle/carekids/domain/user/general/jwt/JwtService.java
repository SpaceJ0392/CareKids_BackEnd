package com.aivle.carekids.domain.user.general.jwt;

import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.User;
import com.aivle.carekids.domain.user.repository.UserRepository;


import java.util.NoSuchElementException;
import com.aivle.carekids.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;
    private final UserRepository userRepository;

    public RefreshToken save(RefreshToken refreshToken) {
        return jwtRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return jwtRepository.findByToken(token);
    }

    public String renewToken(String refreshToken) {
        // token 이 존재하는지 찾고, 존재한다면 RefreshToken 안의 memberId 를 가져와서 member 를 찾은 후 AccessToken 생성
        RefreshToken token = this.findByToken(refreshToken).orElseThrow(NoSuchElementException::new);
        User user = userRepository.findByUserId(token.getUserId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
        return JwtUtils.generateAccessToken(user);
    }
}