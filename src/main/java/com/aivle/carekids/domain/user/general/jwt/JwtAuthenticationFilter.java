package com.aivle.carekids.domain.user.general.jwt;


import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils.verifyToken;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtRepository jwtRepository;

    private static final String[] whitelist = {"/**", "/login", "/loginHome", "/api/signup", "/renew", "/loginSuccess",
            "/login/oauth2/code/**", "/oauth2/signUp", "/error", "/js/**, /h2-console"};

    // 필터를 거치지 않을 URL 을 설정하고, true 를 return 하면 바로 다음 필터를 진행하게 됨
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }


    private boolean isLogout(String accessToken) {
        Integer values = jwtRepository.getValues(accessToken);
        return values == -1;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        String header = request.getHeader(JwtConstants.JWT_HEADER);
        // 1. 토큰이 만료됐는지 안됐는지 --> 만료됐으면, 자체적으로 재발급


        // 토큰이 없거나 + 로그 아웃된 사용자일 경우
        if (header == null || !header.startsWith(JwtConstants.JWT_TYPE)) {
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), "Token 이 존재하지 않습니다");
            return;
        }

        try {
            // 토큰 검증
            String token = JwtUtils.getTokenFromHeader(header);
            if (!isLogout(token)){
                DecodedJWT decodedJWT = verifyToken(token);

                UsernamePasswordAuthenticationToken authenticationToken = JwtUtils.getAuthenticationToken(decodedJWT);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            // 이거 없으면 다음 실행 안됨!!
            doFilter(request, response, filterChain);

        } catch (TokenExpiredException e) {
            // 토큰 만료 시 발생하는 예외
            response.setStatus(SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), "Access Token 이 만료되었습니다.");
        } catch (Exception e) {
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), "올바르지 않은 Token 입니다.");
        }
    }
}