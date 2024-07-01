package com.aivle.carekids.domain.user.general.controller;

import com.aivle.carekids.domain.user.dto.EmailDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.general.service.EmailService;
import com.aivle.carekids.domain.user.general.service.UsersService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UsersService userService;
    private final EmailService emailService;

    // 회원 가입 페이지 입장 API
    @GetMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestParam(required = false) String email,
                                            @RequestParam(value = "social-type", required = false) String socialType){

        return userService.signUp(email, socialType);
    }

    // 회원 가입 시 이메일 인증 API
    @PostMapping("/send-email")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody @Valid EmailDto emailDto) throws MessagingException, NoSuchAlgorithmException {
        return emailService.sendEmail(emailDto.getEmail());
    }

    // 이메일 인증 번호 검증 API
    @PostMapping("/auth-email")
    public  ResponseEntity<Map<String, String>> authEmail (@RequestBody EmailDto emailDto) throws MessagingException, NoSuchAlgorithmException {
        return emailService.verifyEmailCode(emailDto.getEmail(), emailDto.getCode());
    }

    // 회원 가입 시 API (회원가입 complete or denied)
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpData) throws URISyntaxException {
        return userService.signUpRequest(signUpData);
    }

}
