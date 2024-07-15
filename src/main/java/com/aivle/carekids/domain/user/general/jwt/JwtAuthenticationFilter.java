package com.aivle.carekids.domain.user.general.jwt;


import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils.verifyToken;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();
        String accessToken = JwtUtils.getAccessTokenFromCookies(cookies);
        String refreshToken = JwtUtils.getRefreshTokenFromCookies(cookies);

        String url = request.getRequestURI();
        if ((accessToken != null && refreshToken != null) && url.startsWith("/api/admin/")){

            DecodedJWT decodedToken = verifyToken(accessToken);
            String usersRole = JwtUtils.getUsersRole(decodedToken);

            UsernamePasswordAuthenticationToken authenticationToken = JwtUtils.getAuthenticationToken(decodedToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        doFilter(request, response, filterChain);
    }
}