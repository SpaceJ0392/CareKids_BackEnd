package com.aivle.carekids.domain.common.controller;

import com.aivle.carekids.domain.common.dto.HomeDto;
import com.aivle.carekids.domain.common.service.HomeService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.global.Variable.GlobelVar;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;
    private final JwtUtils jwtUtils;

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

        response.sendRedirect(GlobelVar.CLIENT_BASE_URL + "/api/home");
    }

    @GetMapping("/home")
    public ResponseEntity<?> displayHome(@CookieValue(name = "AccessToken", required = false) String accessToken,
                                         @CookieValue(name = "RefreshToken", required = false) String refreshToken){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verifyMap.get("state") != null) {
            HomeDto homeDto = homeService.displayHomeGuest();
            return ResponseEntity.ok(homeDto);
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        HomeDto homeDto = homeService.displayHomeUser(usersId);

        if (homeDto != null) {
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(homeDto);
        }

        return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다."));
    }
}
