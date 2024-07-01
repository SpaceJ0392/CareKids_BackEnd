package com.aivle.carekids.domain.user.general.filter;

import com.aivle.carekids.domain.user.dto.SignInDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class JsonToHttpRequestFilter implements Filter {

    private static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private final ObjectMapper objectMapper;
    private RequestMatcher matcher;

    public JsonToHttpRequestFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.matcher = new AntPathRequestMatcher("/login", "POST");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpRequestWithModifiableParameters request = new HttpRequestWithModifiableParameters((HttpServletRequest) req);

        if (matcher.matches(request)) {
            SignInDto loginData = objectMapper.readValue(request.getInputStream(), SignInDto.class);

            request.setParameter(SPRING_SECURITY_FORM_USERNAME_KEY, loginData.getEmail());
            request.setParameter(SPRING_SECURITY_FORM_PASSWORD_KEY, loginData.getPassword());
        }
        chain.doFilter(request, resp);
    }

}

