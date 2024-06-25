package com.aivle.carekids.domain.user.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

public class OauthController {
    // 구글 로그인
    @PostMapping("/signin/oauth2")
    public ResponseEntity<Map<String, String>> singInOauth(){

        Map<String ,String> message = new HashMap<>();
        message.put("message", "ok");

        return ResponseEntity.ok(message);
    }
}
