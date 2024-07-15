package com.aivle.carekids.admin.place.controller;


import com.aivle.carekids.admin.place.service.PlaceAdminService;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.place.dto.PlaceDetailDto;
import com.aivle.carekids.domain.place.service.PlaceService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PlaceAdminController {

    private final JwtUtils jwtUtils;
    private final PlaceService placeService;
    private final PlaceAdminService placeAdminService;

    @GetMapping("/place")
    public ResponseEntity<?> displayPlacePage(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(placeService.displayPlaceGuest(page - 1, size));
    }

    @GetMapping("/place/{id}")
    public ResponseEntity<?> displayPlaceDetail(@CookieValue(name = "AccessToken") String accessToken,
                                                     @CookieValue(name = "RefreshToken") String refreshToken,
                                                     @PathVariable(name = "id") Long kidsPolicyId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(placeService.placeDetail(kidsPolicyId));
    }

    @PostMapping("/place/edit")
    public ResponseEntity<?> editPlace(@CookieValue(name = "AccessToken") String accessToken,
                                            @CookieValue(name = "RefreshToken") String refreshToken,
                                            @RequestBody PlaceDetailDto placeDetailDto) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return placeAdminService.editPlace(placeDetailDto);
    }

    @DeleteMapping("/place/delete/{id}")
    public ResponseEntity<?> deletePlace(@CookieValue(name = "AccessToken") String accessToken,
                                              @CookieValue(name = "RefreshToken") String refreshToken,
                                              @PathVariable(name = "id") Long placeId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return placeAdminService.deletePlace(placeId);
    }
}
