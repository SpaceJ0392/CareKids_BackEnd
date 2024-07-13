package com.aivle.carekids.admin.kindergarten.controller;

import com.aivle.carekids.admin.kindergarten.service.KindergartenAdminService;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.service.KindergartenService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class KindergartenAdminController {

    private final KindergartenService kindergartenService;
    private final KindergartenAdminService kindergartenAdminService;
    private final JwtUtils jwtUtils;

    @GetMapping("/kindergarten")
    public ResponseEntity<?> displayKindergartenPage(@CookieValue(name = "AccessToken") String accessToken,
                                                 @CookieValue(name = "RefreshToken") String refreshToken,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "20") int size) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(kindergartenService.displayKindergartenGuest(page - 1, size));
    }

    @GetMapping("/kindergarten/{id}")
    public ResponseEntity<?> displayKindergartenDetail(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @PathVariable(name = "id") Long kindergartenId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(kindergartenService.kindergartenDetail(kindergartenId));
    }

    @PostMapping("/kindergarten/edit")
    public ResponseEntity<?> createKindergarten(@CookieValue(name ="AccessToken", required = false) String accessToken,
                                            @CookieValue(name ="RefreshToken", required = false) String refreshToken,
                                            @RequestPart(name = "data") KindergartenDetailDto kindergartenDetailDto){

        // 권한 검사
        // 1. 미로그인 사용자인지
        // 2. 로그아웃된 사용자인지
        // 3. 관리자가 아닌 일반 유저인지
        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        // 액세스 토큰 재설정
        // 액세스 토큰이 재발급 된 사용자일 경우 헤더에 새로운 access_token 추가해줌
        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }

        return ResponseEntity.ok(kindergartenAdminService.editKindergarten(kindergartenDetailDto));
    }

    @DeleteMapping("/kindergarten/delete/{id}")
    public ResponseEntity<?> deleteKindergarten(@CookieValue(name = "AccessToken") String accessToken,
                                            @CookieValue(name = "RefreshToken") String refreshToken,
                                            @PathVariable(name = "id") Long kindergartenId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return kindergartenAdminService.deleteKindergarten(kindergartenId);
    }


}
