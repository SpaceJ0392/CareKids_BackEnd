package com.aivle.carekids.domain.user.oauth2.handler;

import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.validation.SignUpValid;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.oauth2.dto.OAuth2UserDetails;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UsersRepository usersRepository;
    private final SignUpValid signUpValid;
    private static final String BASE_URL = "http://localhost:8080/api";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //이미 존재하는 이메일인지 확인.
        OAuth2UserDetails userDetails = (OAuth2UserDetails) authentication.getPrincipal();

        String redirectUrl;
        Map<String, String> valid = signUpValid.emailValidation(userDetails.getUsername());
        if (!valid.isEmpty()) { // 있으면 redirect /
            // TODO - Json Web Token 줘야 함.
            Users users = usersRepository.findByUsersEmail(userDetails.getUsername());
            String accessToken = JwtUtils.generateAccessToken(users);
            redirectUrl = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/home")
                    .queryParam("accessToken", accessToken)
                    .build().toUriString();

            Cookie access_cookie = new Cookie(JwtConstants.ACCESS, accessToken);
            access_cookie.setMaxAge((int) (JwtConstants.ACCESS_EXP_TIME / 1000));     // 5분 설정
            access_cookie.setHttpOnly(true);
            response.addCookie(access_cookie);


            response.sendRedirect(redirectUrl);


        } else { // 없으면 get redirect email 및 socialtype 넣어서. (회원가입 페이지로)
            redirectUrl = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/signup")
                    .queryParam("email", userDetails.getUsername())
                    .queryParam("social-type", userDetails.getSocialType().toString())
                    .build().toUriString();
            response.sendRedirect(redirectUrl);
        }


    }
}
