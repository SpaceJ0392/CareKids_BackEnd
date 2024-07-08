package com.aivle.carekids.domain.common.controller;

import com.aivle.carekids.domain.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/region-agetag")
    public ResponseEntity<Object> regionAgetag(){
        return ResponseEntity.ok(commonService.regionAgeTagAll());
    }

    @GetMapping("/region")
    public ResponseEntity<Object> regionAll(){
        return ResponseEntity.ok(commonService.regionAll());
    }

    @GetMapping("/age-tag")
    public ResponseEntity<Object> ageTagAll(){
        return ResponseEntity.ok(commonService.ageTagAll());
    }
}
