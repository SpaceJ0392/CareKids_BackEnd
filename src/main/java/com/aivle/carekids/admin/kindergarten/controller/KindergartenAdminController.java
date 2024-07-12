package com.aivle.carekids.admin.kindergarten.controller;

import com.aivle.carekids.admin.kindergarten.dto.KindergartenAdminDto;
import com.aivle.carekids.admin.kindergarten.service.KindergartenAdminService;
import com.aivle.carekids.domain.question.dto.QuestionDetailDto;
import com.aivle.carekids.domain.question.service.QuestionService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class KindergartenAdminController {

    private final KindergartenAdminService kindergartenAdminService;
    private final JwtUtils jwtUtils;

    @PostMapping("/question/edit")
    public ResponseEntity<?> createKindergarten(@CookieValue(name ="AccessToken", required = false) String accessToken,
                                            @CookieValue(name ="RefreshToken", required = false) String refreshToken,
                                            @RequestPart(name = "data") KindergartenAdminDto kindergartenAdminDto){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }

        return ResponseEntity.ok(kindergartenAdminService.editKindergarten(kindergartenAdminDto));
    }


}
