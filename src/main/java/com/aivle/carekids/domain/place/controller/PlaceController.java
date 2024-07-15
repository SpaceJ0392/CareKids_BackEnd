package com.aivle.carekids.domain.place.controller;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.SearchRegionCateDto;
import com.aivle.carekids.domain.place.dto.PlaceDetailDto;
import com.aivle.carekids.domain.place.service.PlaceService;
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
public class PlaceController {

    private final PlaceService placeService;
    private final JwtUtils jwtUtils;

    @GetMapping("/place")
    public ResponseEntity<?> displayPlace(@CookieValue(name = "AccessToken", required = false) String accessToken,
                                                 @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                                                 @RequestParam(value = "page", defaultValue = "1")int page,
                                                 @RequestParam(value = "size", defaultValue = "12")int size){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_ADMIN.getRole())) { // 미가입 OR 로그아웃된 사용자인 경우
            return ResponseEntity.ok(placeService.displayPlaceGuest(page - 1, size));
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }


        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        PageInfoDto pageInfoDto = placeService.displayPlaceUser(usersId, page - 1, size);

        if (pageInfoDto != null){
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pageInfoDto);
        }

        return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다."));
    }

    @GetMapping("/place/{id}")
    public ResponseEntity<?> placeDetail(@PathVariable Long id){

        PlaceDetailDto placeDetailDto = placeService.placeDetail(id);

        if (placeDetailDto != null){ return ResponseEntity.ok(placeDetailDto); }
        return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
    }

    @PostMapping("/place/search")
    public ResponseEntity<Object> searchKindergarten(@RequestBody SearchRegionCateDto searchRegionCateDto,
                                                     @RequestParam(value = "page", defaultValue = "1")int page,
                                                     @RequestParam(value = "size", defaultValue = "12")int size){

        PageInfoDto searchPlaceListDto = placeService.searchPlace(searchRegionCateDto, page - 1, size);
        if (searchPlaceListDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "조회 대상이 존재하지 않습니다."));
        }

        return ResponseEntity.ok(searchPlaceListDto);
    }
}
