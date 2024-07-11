package com.aivle.carekids.domain.user.general.filter;


import com.aivle.carekids.domain.user.general.Authentication.CustomAuthenticationManager;
import com.aivle.carekids.domain.user.general.jwt.JwtService;
import com.aivle.carekids.domain.user.general.jwt.RefreshToken;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.service.CustomUserDetail;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.global.Variable.GlobelVar;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationManager customAuthenticationManager;
    private final JwtService jwtService;
    private ObjectMapper objectMapper;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println(request);
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String role = request.getParameter("role");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);
        setDetails(request, usernamePasswordAuthenticationToken);

        if (role.equals("ROLE_ADMIN")){
            //role이 관리자라면, 토큰을 만들지 않아서, 비밀번호 검출 시, 비밀번호 인코딩 없이 검사
            return customAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        Users users = ((CustomUserDetail) authResult.getPrincipal()).getUsers();

        // 인증이 성공한 경우 토큰을 생성하여, 응답 헤더에 담아 클라이언트에게 전달
        System.out.println(JwtConstants.SECRET_KEY);
        String accessToken = JwtUtils.generateAccessToken(users);
        String refreshToken = JwtUtils.generateRefreshToken(users);

        // 인증이 성공했으니 Refresh Token 을 DB( Redis )에 저장한다
        jwtService.save(new RefreshToken(users.getUsersId(), refreshToken));

        // 헤더로 accessToken 전달
        response.addHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);

        Cookie access_cookie = new Cookie(JwtConstants.ACCESS, accessToken);
        access_cookie.setMaxAge((int) (JwtConstants.ACCESS_EXP_TIME / 1000));     // 5분 설정
        access_cookie.setHttpOnly(true);
        response.addCookie(access_cookie);

        // Refresh Token 은 Cookie 에 담아서 전달하되, XSS 공격 방어를 위해 HttpOnly 를 설정한다
        Cookie refresh_cookie = new Cookie(JwtConstants.REFRESH, refreshToken);
        refresh_cookie.setMaxAge((int) (JwtConstants.REFRESH_EXP_TIME / 1000));     // 5분 설정
        refresh_cookie.setHttpOnly(true);
        response.addCookie(refresh_cookie);

        response.sendRedirect(GlobelVar.CLIENT_BASE_URL);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", System.currentTimeMillis());
        data.put("message", "Invalid login credentials");

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }

}