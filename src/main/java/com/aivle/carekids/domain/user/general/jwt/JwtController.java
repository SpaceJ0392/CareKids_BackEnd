package com.aivle.carekids.domain.user.general.jwt;

import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/renew")
    public ResponseEntity<?> renewToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = JwtUtils.getTokenFromHeader(request.getHeader(JwtConstants.JWT_HEADER));
            String accessToken = jwtService.renewToken(refreshToken);
            response.addHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);
            return ResponseEntity.ok(JwtConstants.JWT_TYPE + accessToken);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Refresh Token 이 만료되었습니다");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}