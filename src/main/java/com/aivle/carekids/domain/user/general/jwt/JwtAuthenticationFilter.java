package com.aivle.carekids.domain.user.general.jwt;


import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Role;
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
import java.nio.file.AccessDeniedException;
import java.util.Objects;

import static com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils.verifyToken;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String url = request.getRequestURI();
        if (url.startsWith("/api/admin/")){
            Cookie[] cookies = request.getCookies();
            String accessToken = JwtUtils.getAccessTokenFromCookies(cookies);
            String refreshToken = JwtUtils.getRefreshTokenFromCookies(cookies);
            if (accessToken == null || refreshToken == null){
                throw new AccessDeniedException("denied Error");
            }

            DecodedJWT decodedToken = verifyToken(accessToken);
            String usersRole = JwtUtils.getUsersRole(decodedToken);
            if (!Objects.equals(usersRole, Role.ROLE_ADMIN.getRole())){
                throw new AccessDeniedException("denied Error");
            }
            else {
                UsernamePasswordAuthenticationToken authenticationToken = JwtUtils.getAuthenticationToken(decodedToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        doFilter(request, response, filterChain);
    }
}