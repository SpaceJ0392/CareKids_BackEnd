package com.aivle.carekids.domain.playInfo.controller;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.SearchAgeTagDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDetailDto;
import com.aivle.carekids.domain.playInfo.service.PlayInfoService;
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
public class PlayInfoController {

    private final PlayInfoService playInfoService;
    private final JwtUtils jwtUtils;

    @GetMapping("/playinfo")
    public ResponseEntity<?> displayPlayInfo(@CookieValue(name = "AccessToken", required = false) String accessToken,
                                                 @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                                                 @RequestParam(value = "page", defaultValue = "1")int page,
                                                 @RequestParam(value = "size", defaultValue = "12")int size){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ADMIN.getRole())) { // 미가입 OR 로그아웃된 사용자인 경우
            return ResponseEntity.ok(playInfoService.displayPlayInfoGuest(page - 1, size));
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }


        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        PageInfoDto pageInfoDto = playInfoService.displayPlayInfoUser(usersId, page - 1, size);

        if (pageInfoDto != null){
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pageInfoDto);
        }

        return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다."));
    }

    @GetMapping("/playinfo/{id}")
    public ResponseEntity<?> playinfoDetail(@PathVariable Long id){

        PlayInfoDetailDto playInfoDetailDto = playInfoService.playInfoDetail(id);

        if (playInfoDetailDto != null){ return ResponseEntity.ok(playInfoDetailDto); }
        return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
    }

    @PostMapping("/playinfo/search")
    public ResponseEntity<Object> searchKindergarten(@RequestBody SearchAgeTagDto searchAgeTagDto,
                                                     @RequestParam(value = "page", defaultValue = "1")int page,
                                                     @RequestParam(value = "size", defaultValue = "12")int size){

        PageInfoDto searchPlayInfoListDto = playInfoService.searchPlayInfo(searchAgeTagDto, page - 1, size);
        if (searchPlayInfoListDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "조회 대상이 존재하지 않습니다."));
        }

        return ResponseEntity.ok(searchPlayInfoListDto);
    }
}
