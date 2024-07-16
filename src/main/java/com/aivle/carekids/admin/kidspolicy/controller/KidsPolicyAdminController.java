package com.aivle.carekids.admin.kidspolicy.controller;


import com.aivle.carekids.admin.kidspolicy.service.KidsPolicyAdminService;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.kidspolicy.service.KidsPolicyService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class KidsPolicyAdminController {

    private final JwtUtils jwtUtils;
    private final KidsPolicyService kidsPolicyService;
    private final KidsPolicyAdminService kidsPolicyAdminService;

    @GetMapping("/kids-policy")
    public ResponseEntity<?> displayKidsPolicyPage(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(kidsPolicyAdminService.displayKidsPolicyPage(page - 1, size));
    }

    @GetMapping("/kids-policy/{id}")
    public ResponseEntity<?> displayKidsPolicyDetail(@CookieValue(name = "AccessToken") String accessToken,
                                                     @CookieValue(name = "RefreshToken") String refreshToken,
                                                     @PathVariable(name = "id") Long kidsPolicyId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(kidsPolicyService.kidsPolicyDetail(kidsPolicyId));
    }

    @PostMapping("/kids-policy/edit")
    public ResponseEntity<?> editKidsPolicy(@CookieValue(name = "AccessToken") String accessToken,
                                            @CookieValue(name = "RefreshToken") String refreshToken,
                                            @RequestBody KidsPolicyDetailDto kidsPolicyDetailDto) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        return kidsPolicyAdminService.editKidsPolicy(kidsPolicyDetailDto, usersId);
    }

    @DeleteMapping("/kids-policy/delete/{id}")
    public ResponseEntity<?> deleteKidsPolicy(@CookieValue(name = "AccessToken") String accessToken,
                                            @CookieValue(name = "RefreshToken") String refreshToken,
                                            @PathVariable(name = "id") Long kidsPolicyId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return kidsPolicyAdminService.deleteKidsPolicy(kidsPolicyId);
    }
}
