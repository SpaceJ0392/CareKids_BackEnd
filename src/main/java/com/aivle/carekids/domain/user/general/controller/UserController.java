package com.aivle.carekids.domain.user.general.controller;

import com.aivle.carekids.domain.user.dto.SignInDto;
import com.aivle.carekids.domain.user.dto.SignUpDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.general.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 회원 가입 페이지 입장 API
    @GetMapping("/signup")
    public ResponseEntity<SignUpDto> signUp(){
        return userService.signUp();
    }

    // 회원 가입 시 API (회원가입 complete or denied)
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpData) throws URISyntaxException {
        return userService.signUpRequest(signUpData);
    }

//    // 로그인 API (일반 로그인)
//    @PostMapping("/signin")
//    public ResponseEntity<Map<String, String>> singIn(@RequestBody SignInDto signInDto){
//        return userService.signIn(signInDto);
//    }

}
