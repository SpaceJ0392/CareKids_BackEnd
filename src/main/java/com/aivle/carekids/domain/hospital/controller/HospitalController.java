package com.aivle.carekids.domain.hospital.controller;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
import com.aivle.carekids.domain.hospital.service.HospitalService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    private final JwtUtils jwtUtils;

    @GetMapping("/hospital")
    public ResponseEntity<?> displayHospital(@CookieValue(name = "AccessToken", required = false) String accessToken,
                                             @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                                             @RequestParam(value = "page", defaultValue = "1")int page,
                                             @RequestParam(value = "size", defaultValue = "12")int size){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ADMIN.getRole())) {
            return ResponseEntity.ok(hospitalService.displayHospitalGuest(page - 1, size));
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        PageInfoDto pageInfoDto = hospitalService.displayHospitalUser(usersId, page - 1, size);

        if (pageInfoDto != null){
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pageInfoDto);
        }

        return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다."));
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<?> hospitalDetail(@PathVariable Long id){

        HospitalDetailDto hospitalDetailDto = hospitalService.hospitalDetail(id);

        if (hospitalDetailDto != null){ return ResponseEntity.ok(hospitalDetailDto); }
        return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
    }

    @PostMapping("/hospital/search")
    public ResponseEntity<Object> searchHospital(@RequestBody SearchRegionDto searchRegionDto,
                                                 @RequestParam(value = "page", defaultValue = "1")int page,
                                                 @RequestParam(value = "size", defaultValue = "12")int size){

        PageInfoDto searchHospitalListDto = hospitalService.searchHospital(searchRegionDto, page - 1, size);
        if (searchHospitalListDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "조회 대상이 존재하지 않습니다."));
        }

        return ResponseEntity.ok(searchHospitalListDto);
    }
}