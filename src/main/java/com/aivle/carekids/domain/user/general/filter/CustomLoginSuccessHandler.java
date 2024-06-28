package com.aivle.carekids.domain.user.general.filter;

import com.aivle.carekids.domain.user.general.jwt.JwtService;
import com.aivle.carekids.domain.user.general.jwt.RefreshToken;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.service.CustomUserDetail;
import com.aivle.carekids.domain.user.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 인증 성공 후 처리되는 핸들러이기 때문에 전달받은 authentication 은 인증이 완료된 객체
        User user = ((CustomUserDetail) authentication.getPrincipal()).getUser();

        // 인증이 성공한 경우 토큰을 생성하여, 응답 헤더에 담아 클라이언트에게 전달
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
}