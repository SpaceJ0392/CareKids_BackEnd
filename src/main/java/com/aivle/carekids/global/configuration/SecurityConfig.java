package com.aivle.carekids.global.configuration;


import com.aivle.carekids.domain.user.general.Authentication.CustomAuthenticationManager;
import com.aivle.carekids.domain.user.general.filter.JsonToHttpRequestFilter;
import com.aivle.carekids.domain.user.general.filter.LoginFilter;
import com.aivle.carekids.domain.user.general.handler.CustomAccessDeniedHandler;
import com.aivle.carekids.domain.user.general.jwt.JwtAuthenticationFilter;
import com.aivle.carekids.domain.user.general.jwt.JwtRepository;
import com.aivle.carekids.domain.user.general.jwt.JwtService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.service.LogoutService;
import com.aivle.carekids.domain.user.oauth2.handler.OAuth2SuccessHandler;
import com.aivle.carekids.domain.user.oauth2.service.CustomOAuth2UserService;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.Variable.GlobelVar;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtRepository jwtRepository;
    private final UsersRepository usersRepository;
    private final LogoutService logoutService;
    private final ObjectMapper objectMapper;
    private final CustomAuthenticationManager customAuthenticationManager;

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtUtils jwtUtils;

    private final AuthenticationConfiguration authenticationConfiguration;
    @Value("${spring.jwt.secret}")
    private String secret;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /* H2 console 무시 */
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true") //false면 접근 불가...
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers("/error", "/favicon.ico");
    }

    /* 권한 부여 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LoginFilter lf = new LoginFilter(authenticationManager(authenticationConfiguration), customAuthenticationManager, new JwtService(jwtRepository, usersRepository), new ObjectMapper());

        // CORS 설정 추가
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("http://localhost:3000"); // React 서버 주소
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        http
                //REST API 설정
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable()) // X-Frame-Options 비활성화
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        requests -> requests.requestMatchers(antMatcher("/api/admin/**")).hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new JsonToHttpRequestFilter(objectMapper, usersRepository), lf.getClass())
                .addFilterAt(lf, UsernamePasswordAuthenticationFilter.class)
                //oauth2 설정
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler))
                .logout(logoutConfig -> logoutConfig
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            SecurityContextHolder.clearContext();
                            response.sendRedirect(GlobelVar.CLIENT_BASE_URL + "/login");
                        })))
                // CORS 설정 추가
                .cors(cors -> cors.configurationSource(source));

        //TODO - Access Denied 문제 해결
        http.exceptionHandling(handler -> handler.accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper)));

        return http.build();
    }

    //* 비밀번호 암호화 bean */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
