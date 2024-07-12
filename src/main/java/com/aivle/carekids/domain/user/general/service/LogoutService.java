package com.aivle.carekids.domain.user.general.service;

import com.aivle.carekids.domain.user.general.jwt.JwtRepository;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final JwtRepository jwtRepository;

    private String secretKey = JwtConstants.SECRET_KEY;

    @Transactional
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        Cookie[] cookies = request.getCookies();

        String accessToken = JwtUtils.getAccessTokenFromCookies(cookies);

        System.out.println(cookies);
        System.out.println(accessToken);


        if(accessToken == null || accessToken.isBlank()){
            throw new RuntimeException("access token과 함께 요청하십시오.");
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        System.out.println(usersId);

//        try {
//            jwtRepository.DeleteRefreshTokenByUsersId(usersId);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        jwtRepository.DeleteRefreshToken(usersId);
        jwtRepository.addBlackList(accessToken); // accesstoken을 블랙리스트로 추가

    }
}
