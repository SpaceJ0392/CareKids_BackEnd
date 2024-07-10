package com.aivle.carekids.domain.kindergarten.controller;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.service.KindergartenService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KindergartenController {


    private final KindergartenService kindergartenService;
    private final JwtUtils jwtUtils;

    @GetMapping("/kindergarten")
    public ResponseEntity<?> displayKindergarten(@CookieValue(name = "AccessToken", required = false) String accessToken,
                                                 @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                                                 @RequestParam(value = "page", defaultValue = "1")int page,
                                                 @RequestParam(value = "size", defaultValue = "12")int size){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verifyMap.get("state") != null) { // 미가입 OR 로그아웃된 사용자인 경우
            return ResponseEntity.ok(kindergartenService.displayKindergartenGuest(page - 1, size));
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }


        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        PageInfoDto pageInfoDto = kindergartenService.displayKindergartenUser(usersId, page - 1, size);

        if (pageInfoDto != null){
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pageInfoDto);
        }

        return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다."));
    }

    @GetMapping("/kindergarten/{id}")
    public ResponseEntity<?> kindergartenDetail(@PathVariable Long id){

        KindergartenDetailDto kindergartenDetailDto = kindergartenService.kindergartenDetail(id);

        if (kindergartenDetailDto != null){ return ResponseEntity.ok(kindergartenDetailDto); }
        return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
    }

    @PostMapping("/kindergarten/search")
    public ResponseEntity<Object> searchKindergarten(@RequestBody SearchRegionDto searchRegionDto,
                                                 @RequestParam(value = "page", defaultValue = "1")int page,
                                                 @RequestParam(value = "size", defaultValue = "12")int size){

        PageInfoDto searchKindergartenListDto = kindergartenService.searchKindergarten(searchRegionDto, page - 1, size);
        if (searchKindergartenListDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "조회 대상이 존재하지 않습니다."));
        }

        return ResponseEntity.ok(searchKindergartenListDto);
    }
}
