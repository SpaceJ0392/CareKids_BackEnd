package com.aivle.carekids.domain.common.controller;

import com.aivle.carekids.domain.common.service.HomeService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/redirect-home")
    public void homeRedirect(@RequestParam(name ="accessToken", required = true) String accessToken,
                             @RequestParam(name ="refreshToken", required = true) String refreshToken,
                             RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {

        Cookie accessCookie = new Cookie(JwtConstants.ACCESS, accessToken);
        accessCookie.setMaxAge((int) (JwtConstants.ACCESS_EXP_TIME / 1000));     // 5분 설정
        accessCookie.setHttpOnly(true);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie(JwtConstants.REFRESH, refreshToken);
        refreshCookie.setMaxAge((int) (JwtConstants.REFRESH_EXP_TIME / 1000));     // 5분 설정
        refreshCookie.setHttpOnly(true);
        response.addCookie(refreshCookie);

        response.sendRedirect("http://localhost:8080");
    }

    @GetMapping("/home")
    public ResponseEntity<?> displayHome(){
        return ResponseEntity.ok(homeService.displayHome());
    }
}
