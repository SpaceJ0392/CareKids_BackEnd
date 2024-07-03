package com.aivle.carekids.domain.kindergarten.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KindergartenController {

    @GetMapping("/kindergarten")
    public ResponseEntity<?> listKindergarten(@RequestHeader(name = "Authorization", required = false) String authorization){
        if (authorization.isEmpty()){ //미가입 사용자
            int a = 1;
        }
        //TODO - 사용자 검증
        return null;
    }
}
