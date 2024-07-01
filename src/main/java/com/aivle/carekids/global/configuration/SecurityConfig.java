package com.aivle.carekids.global.configuration;

import com.aivle.carekids.domain.user.general.filter.JsonToHttpRequestFilter;
import com.aivle.carekids.domain.user.general.jwt.JwtRepository;
import com.aivle.carekids.domain.user.general.jwt.JwtService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.filter.LoginFilter;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    @Value("${spring.jwt.secret}") private String secret;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    private final JwtRepository jwtRepository;
    private final UsersRepository usersRepository;


    /* H2 console 무시 */
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true") //false면 접근 불가...
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    /* 권한 부여 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        LoginFilter lf = new LoginFilter(authenticationManager(authenticationConfiguration), new JwtService(jwtRepository, usersRepository));

        return http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers(antMatcher("/admin/**")).hasRole("ADMIN")
                                .requestMatchers(new MvcRequestMatcher(introspector, "/login"), new MvcRequestMatcher(introspector, "/"), new MvcRequestMatcher(introspector, "/signup")).permitAll()
                                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(new JsonToHttpRequestFilter(new ObjectMapper()), lf.getClass())
                .addFilterAt(lf, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //* 비밀번호 암호화 bean */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
