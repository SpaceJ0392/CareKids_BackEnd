package com.aivle.carekids.domain.user.oauth2.handler;

import com.aivle.carekids.domain.user.general.jwt.JwtService;
import com.aivle.carekids.domain.user.general.jwt.RefreshToken;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.validation.SignUpValid;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.oauth2.dto.OAuth2UserDetails;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.Variable.GlobelVar;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UsersRepository usersRepository;
    private final SignUpValid signUpValid;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //이미 존재하는 이메일인지 확인.
        OAuth2UserDetails userDetails = (OAuth2UserDetails) authentication.getPrincipal();

        String redirectUrl;
        Map<String, String> valid = signUpValid.emailValidation(userDetails.getUsername());
        if (!valid.isEmpty()) { // 있으면 redirect /
            //Json Web Token
            Users users = usersRepository.findByUsersEmail(userDetails.getUsername());
            String accessToken = JwtUtils.generateAccessToken(users);
            String refreshToken = JwtUtils.generateRefreshToken(users);

            jwtService.save(new RefreshToken(users.getUsersId(), refreshToken));
            redirectUrl = UriComponentsBuilder.fromHttpUrl(GlobelVar.SERVER_BASE_URL).path("/redirect-home")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build().toUriString();

            response.sendRedirect(redirectUrl);

        } else { // 없으면 get redirect email 및 socialtype 넣어서. (회원가입 페이지로)
            redirectUrl = UriComponentsBuilder.fromHttpUrl(GlobelVar.CLIENT_BASE_URL).path("/signup")
                    .queryParam("email", userDetails.getUsername())
                    .queryParam("social-type", userDetails.getSocialType().toString())
                    .build().toUriString();
            response.sendRedirect(redirectUrl);
        }


    }
}
