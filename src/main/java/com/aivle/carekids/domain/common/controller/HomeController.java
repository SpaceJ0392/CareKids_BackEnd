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
                                        RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {

        Cookie accessCookie = new Cookie(JwtConstants.ACCESS, accessToken);
        accessCookie.setMaxAge((int) (JwtConstants.ACCESS_EXP_TIME / 1000));     // 5분 설정
        accessCookie.setHttpOnly(true);
        response.addCookie(accessCookie);

        response.sendRedirect("http://localhost:8080/api/home");
    }

    @GetMapping("/home")
    public ResponseEntity<?> displayHome(){
        return ResponseEntity.ok(homeService.displayHome());
    }
}
