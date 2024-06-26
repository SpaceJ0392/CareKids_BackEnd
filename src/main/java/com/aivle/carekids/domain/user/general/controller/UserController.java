package com.aivle.carekids.domain.user.general.controller;

import com.aivle.carekids.domain.user.dto.SignInDto;
import com.aivle.carekids.domain.user.dto.SignUpDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.general.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 페이지 입장 API
    //TODO - oauth2로 들어올 경우, url param으로 email 등이 넘어옴.
    @GetMapping("/signup")
    public ResponseEntity<SignUpDto> signUp(@RequestParam(required = false) String email,
                                            @RequestParam(required = false) String socialType){

        return userService.signUp();
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
