package com.aivle.carekids.domain.common.controller;

import com.aivle.carekids.domain.common.dto.HomeDto;
import com.aivle.carekids.domain.common.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<HomeDto> home(){
        return ResponseEntity.ok(homeService.displayHome());
    }


}
