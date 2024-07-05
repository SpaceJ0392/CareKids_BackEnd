package com.aivle.carekids.domain.hospital.controller;

import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import com.aivle.carekids.domain.hospital.service.HospitalService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    private final JwtUtils jwtUtils;

    @GetMapping("/hospital")
    public ResponseEntity<?> displayHospital(@CookieValue(name = "AccessToken", required = false) String accessToken,
                                             @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                                             Pageable pageable){
        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verifyMap.get("state") != null) {
            return ResponseEntity.ok(hospitalService.displayHospitalGuest(pageable));
        }


        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        HospitalListDto hospitalListDto = hospitalService.displayHospitalUser(usersId, pageable);

        if (hospitalListDto != null){
            if (verifyMap.get("access_token") != null){
                return ResponseEntity.ok(Map.of("new_access_token", verifyMap.get("access_token"),
                        "data", hospitalListDto));
            }

            return ResponseEntity.ok(hospitalListDto);
        }

        return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
    }
}
