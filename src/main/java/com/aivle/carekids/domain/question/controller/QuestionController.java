package com.aivle.carekids.domain.question.controller;

import com.aivle.carekids.domain.question.dto.QuestionDetailDisplayDto;
import com.aivle.carekids.domain.question.dto.QuestionDetailDto;
import com.aivle.carekids.domain.question.service.QuestionService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final JwtUtils jwtUtils;

    @GetMapping("/question")
    public ResponseEntity<?> displayQuestion(@RequestParam(value = "page", defaultValue = "1")int page,
                                             @RequestParam(value = "size", defaultValue = "20")int size){

        return ResponseEntity.ok(questionService.displayQuestion(page - 1, size));
    }


    @GetMapping("/question/{id}")
    public ResponseEntity<?> questionDetail(@PathVariable(name = "id") Long questionId,
                                            @CookieValue(name ="AccessToken", required = false) String accessToken,
                                            @CookieValue(name ="RefreshToken", required = false) String refreshToken){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "로그인이 필요합니다."));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        QuestionDetailDisplayDto questionDetailDisplayDto = questionService.displayQuestionDetail(questionId, usersId);

        if (questionDetailDisplayDto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", " 잠겨 있는 글입니다."));
        }

        return ResponseEntity.ok(questionDetailDisplayDto);
    }


    @PostMapping("/question/edit")
    public ResponseEntity<?> createQuestion(@CookieValue(name ="AccessToken", required = false) String accessToken,
                                            @CookieValue(name ="RefreshToken", required = false) String refreshToken,
                                            @RequestPart(name = "data") QuestionDetailDto questionDetailDto,
                                            @RequestPart(name = "files", required = false) List<MultipartFile> multipartFiles){

        Map<String, String> verifyMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (verifyMap.get("state") != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "로그인이 필요합니다."));
        }

        HttpHeaders headers = new HttpHeaders();
        if (verifyMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, verifyMap.get("access_token"));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        Map<String, String> result = questionService.editQuestion(questionDetailDto, multipartFiles, usersId);
        if (result == null) {
            return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(result);
    }


    @DeleteMapping("/question/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(name = "id") Long questionId,
                                            @CookieValue(name ="AccessToken", required = false) String accessToken,
                                            @CookieValue(name ="RefreshToken", required = false) String refreshToken){

        Map<String, String> tokenMap = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (tokenMap.get("state") != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "로그인이 필요합니다."));
        }

        HttpHeaders headers = new HttpHeaders();
        if (tokenMap.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, tokenMap.get("access_token"));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        Map<String, String> result = questionService.deleteQuestion(questionId, usersId);
        if (result == null){
            return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "잘못된 접근입니다."));
        }

        return ResponseEntity.status((HttpStatus.OK)).headers(headers).body(result);
    }
}
