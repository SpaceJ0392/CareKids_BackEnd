package com.aivle.carekids.domain.user.general.filter;


import com.aivle.carekids.domain.user.general.jwt.JwtService;
import com.aivle.carekids.domain.user.general.jwt.RefreshToken;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.service.CustomUserDetail;
import com.aivle.carekids.domain.user.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


@AllArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
//    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println(request);
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);

        setDetails(request, usernamePasswordAuthenticationToken);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 토큰 생성 후, ID와 PW 담아 매니저에게 전달
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("success");
        System.out.println("this is request : "+request);
        System.out.println("this is chain : "+chain);
        System.out.println("this is authResult : "+authResult);

        User user = ((CustomUserDetail) authResult.getPrincipal()).getUser();

        // 인증이 성공한 경우 토큰을 생성하여, 응답 헤더에 담아 클라이언트에게 전달
        System.out.println(JwtConstants.SECRET_KEY);
        String accessToken = JwtUtils.generateAccessToken(user);
        String refreshToken = JwtUtils.generateRefreshToken(user);

        // 인증이 성공했으니 Refresh Token 을 DB( Redis )에 저장한다
        jwtService.save(new RefreshToken(refreshToken, user.getUserId()));

        // 헤더로 accessToken 전달
        response.addHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);
        // Refresh Token 은 Cookie 에 담아서 전달하되, XSS 공격 방어를 위해 HttpOnly 를 설정한다
        Cookie cookie = new Cookie(JwtConstants.REFRESH, refreshToken);
        cookie.setMaxAge((int) (JwtConstants.REFRESH_EXP_TIME / 1000));     // 5분 설정
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("fail");
    }

}