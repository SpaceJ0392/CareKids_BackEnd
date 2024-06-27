package com.aivle.carekids.domain.user.general.controller;

import com.aivle.carekids.domain.user.dto.EmailDto;
import com.aivle.carekids.domain.user.dto.SignInDto;
import com.aivle.carekids.domain.user.dto.SignUpDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.general.service.EmailService;
import com.aivle.carekids.domain.user.general.service.UserService;
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

    private final UserService userService;
    private final EmailService emailService;

    // 회원 가입 페이지 입장 API
    @GetMapping("/signup")
    public ResponseEntity<SignUpDto> signUp(@RequestParam(required = false) String email,
                                            @RequestParam(required = false) String socialType){

        return userService.signUp();
    }

    // 회원 가입 시 이메일 인증 API
    @PostMapping("/signup/send-email")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException, NoSuchAlgorithmException {
        return emailService.sendEmail(emailDto.getEmail());
    }

    // 이메일 인증 번호 검증 API
    @PostMapping("/signup/auth-email")
    public  ResponseEntity<Map<String, String>> authEmail (@RequestBody EmailDto emailDto) throws MessagingException, NoSuchAlgorithmException {
        return emailService.verifyEmailCode(emailDto.getEmail(), emailDto.getCode());
    }

    // 회원 가입 시 API (회원가입 complete or denied)
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpData) throws URISyntaxException {
        return userService.signUpRequest(signUpData);
    }

    // 로그인 API (일반 로그인)
    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> singIn(@RequestBody SignInDto signInDto){
        return userService.signIn();
    }

}
