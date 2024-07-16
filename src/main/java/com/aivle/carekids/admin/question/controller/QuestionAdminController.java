package com.aivle.carekids.admin.question.controller;

import com.aivle.carekids.admin.question.dto.QuestionAnswerDto;
import com.aivle.carekids.admin.question.service.QuestionAdminService;
import com.aivle.carekids.domain.question.service.QuestionService;
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
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class QuestionAdminController {

    private final QuestionService questionService;
    private final QuestionAdminService questionAdminService;
    private final JwtUtils jwtUtils;


    @GetMapping("/question")
    public ResponseEntity<?> displayQuestionPage(@CookieValue(name = "AccessToken") String accessToken,
                                                 @CookieValue(name = "RefreshToken") String refreshToken,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "20") int size) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        return ResponseEntity.ok(questionAdminService.displayQuestion(page - 1, size));
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<?> displayQuestionDetail(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @PathVariable(name = "id") Long questionId) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        return ResponseEntity.ok(questionService.displayQuestionDetail(questionId, usersId));
    }

    @PostMapping("/question/edit-answer")
    public ResponseEntity<?> editAnswerForQuestion(@CookieValue(name = "AccessToken") String accessToken,
                                                   @CookieValue(name = "RefreshToken") String refreshToken,
                                                   @RequestBody QuestionAnswerDto questionAnswerDto) {

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null || Objects.equals(verifyMap.get("사용자 role"), Role.ROLE_USER.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        return questionAdminService.editAnswerForQuestion(questionAnswerDto);
    }
}
