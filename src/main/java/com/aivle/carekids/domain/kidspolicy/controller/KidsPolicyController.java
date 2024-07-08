package com.aivle.carekids.domain.kidspolicy.controller;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.SearchRegionAgeTagDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.kidspolicy.service.KidsPolicyService;
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
public class KidsPolicyController {

    private final KidsPolicyService kidsPolicyService;
    private final JwtUtils jwtUtils;

    @GetMapping("/kids-policy")
    public ResponseEntity<?> displayKidsPolicy(@CookieValue(name = "AccessToken", required = false) String accessToken,
                                               @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                                               @RequestParam(value = "page", defaultValue = "1")int page,
                                               @RequestParam(value = "size", defaultValue = "20")int size){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verifyMap.get("state") != null) {
            return ResponseEntity.ok(kidsPolicyService.displayKidsPolicyGuest(page - 1, size));
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        PageInfoDto pageInfoDto = kidsPolicyService.displayKidsPolicyUser(usersId, page - 1, size);

        if (pageInfoDto != null){
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pageInfoDto);
        }

        return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다."));
    }

    @GetMapping("/kids-policy/{id}")
    public ResponseEntity<?> kidsPolicyDetail(@PathVariable Long id){

        KidsPolicyDetailDto kidsPolicyDetailDto = kidsPolicyService.kidsPolicyDetail(id);

        if (kidsPolicyDetailDto != null){ return ResponseEntity.ok(kidsPolicyDetailDto); }
        return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
    }

    @PostMapping("/kids-policy/search")
    public ResponseEntity<Object> searchKidsPolicy(@RequestBody SearchRegionAgeTagDto searchRegionAgeTagDto,
                                                   @RequestParam(value = "page", defaultValue = "1")int page,
                                                   @RequestParam(value = "size", defaultValue = "20")int size){

        PageInfoDto searchKidsPolicyListDto = kidsPolicyService.searchKidsPolicy(searchRegionAgeTagDto, page - 1, size);
        if (searchKidsPolicyListDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "조회 대상이 존재하지 않습니다."));
        }

        return ResponseEntity.ok(searchKidsPolicyListDto);
    }
}
