package com.aivle.carekids.admin.hospital.controller;

import com.aivle.carekids.admin.hospital.service.HospitalAdminService;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
import com.aivle.carekids.domain.hospital.service.HospitalService;
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
public class HospitalAdminController {

    private final JwtUtils jwtUtils;
    private final HospitalService hospitalService;
    private final HospitalAdminService hospitalAdminService;

    @GetMapping("/hospital")
    public ResponseEntity<?> displayHospitalPage(@CookieValue(name = "AccessToken") String accessToken,
                                                 @CookieValue(name = "RefreshToken") String refreshToken,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "20") int size) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(hospitalAdminService.displayHospitalPage(page - 1, size));
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<?> displayHospitalDetail(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @PathVariable(name = "id") Long hospitalId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(hospitalService.hospitalDetail(hospitalId));
    }

    @PostMapping("/hospital/edit")
    public ResponseEntity<?> editHospital(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @RequestBody HospitalDetailDto hospitalDetailDto) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return hospitalAdminService.editHospital(hospitalDetailDto);
    }

    @DeleteMapping("/hospital/delete/{id}")
    public ResponseEntity<?> deleteHospital(@CookieValue(name = "AccessToken") String accessToken,
                                            @CookieValue(name = "RefreshToken") String refreshToken,
                                            @PathVariable(name = "id") Long hospitalId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return hospitalAdminService.deleteHospital(hospitalId);
    }

}
