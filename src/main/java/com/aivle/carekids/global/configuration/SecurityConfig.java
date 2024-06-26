package com.aivle.carekids.global.configuration;

import com.aivle.carekids.domain.user.oauth2.handler.OAuth2SuccessHandler;
import com.aivle.carekids.domain.user.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    /* H2 console 무시 */
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true") //false면 접근 불가...
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
                //.requestMatchers("/error", "/favicon.ico");
    }

    /* 권한 부여 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        //REST API 설정
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
        .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
        .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable()) // X-Frame-Options 비활성화
        .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 인가 설정
        .authorizeHttpRequests(
                requests -> requests.requestMatchers(antMatcher("/admin/**")).hasRole("ADMIN")
                        .anyRequest().permitAll()
        )
        //oauth2 설정
        .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                                .successHandler(oAuth2SuccessHandler))
        ;

        return http.build();
    }


    //* 비밀번호 암호화 bean */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
