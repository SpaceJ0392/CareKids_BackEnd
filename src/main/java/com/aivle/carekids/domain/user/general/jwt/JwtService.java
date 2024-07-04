package com.aivle.carekids.domain.user.general.jwt;

import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.exception.BusinessLogicException;
import com.aivle.carekids.global.exception.ExceptionCode;
import com.aivle.carekids.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;
    private final UsersRepository usersRepository;

    public RefreshToken save(RefreshToken refreshToken) {
        return jwtRepository.save(refreshToken);
    }

//    public Optional<RefreshToken> findByToken(String token) {
//        return jwtRepository.findByToken(token);
//    }

    public String renewToken(String refreshToken) {
        // token 이 존재하는지 찾고, 존재한다면 RefreshToken 안의 memberId 를 가져와서 member 를 찾은 후 AccessToken 생성
//        RefreshToken token = this.findByToken(refreshToken).orElseThrow(NoSuchElementException::new);
        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(refreshToken));
        System.out.println(usersId);
        RefreshToken redisRefreshToken = jwtRepository.findRefreshTokenByUsersId(usersId).orElseThrow(() -> new NullPointerException("Refresh Token이 없습니다. 재로그인해주세요."));
        if (Objects.equals(redisRefreshToken.getToken(), refreshToken)){
            Users users = usersRepository.findByUsersId(usersId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
            return JwtUtils.generateAccessToken(users);
        }
        else throw new BusinessLogicException(ExceptionCode.TOKEN_IS_NOT_SAME);
    }
}