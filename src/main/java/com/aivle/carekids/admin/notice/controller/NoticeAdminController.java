package com.aivle.carekids.admin.notice.controller;

import com.aivle.carekids.admin.notice.service.NoticeAdminService;
import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.notice.service.NoticeService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class NoticeAdminController {

    private final NoticeService noticeService;
    private final NoticeAdminService noticeAdminService;
    private final JwtUtils jwtUtils;

    @GetMapping("/notice")
    public ResponseEntity<?> displayQuestionPage(@CookieValue(name = "AccessToken") String accessToken,
                                                 @CookieValue(name = "RefreshToken") String refreshToken,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "20") int size) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(noticeAdminService.listNotice(page - 1, size));
    }

    @GetMapping("/notice/{id}")
    public ResponseEntity<?> displayQuestionDetail(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @PathVariable(name = "id") Long noticeId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return noticeService.noticeDetail(noticeId);
    }

    @PostMapping("/notice/edit")
    public ResponseEntity<?> editNotice(@CookieValue(name = "AccessToken") String accessToken,
                                        @CookieValue(name = "RefreshToken") String refreshToken,
                                        @RequestPart(name = "data") NoticeDto noticeDto,
                                        @RequestPart(name = "file", required = false) MultipartFile imgFile) throws IOException {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        return noticeAdminService.editNotice(usersId, noticeDto, imgFile);
    }

    @DeleteMapping("notice/delete/{id}")
    public ResponseEntity<?> deleteNotice(@CookieValue(name = "AccessToken") String accessToken,
                                          @CookieValue(name = "RefreshToken") String refreshToken,
                                          @PathVariable(name = "id") Long noticeId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        Map<String, ?> result = noticeAdminService.deleteNotice(usersId, noticeId);

        if (result == null){
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
        }

        return ResponseEntity.ok(result);
    }

}
