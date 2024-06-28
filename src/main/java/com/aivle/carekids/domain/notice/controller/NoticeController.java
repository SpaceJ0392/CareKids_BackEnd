package com.aivle.carekids.domain.notice.controller;

import com.aivle.carekids.domain.notice.dto.PageInfoDto;
import com.aivle.carekids.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notice")
    public ResponseEntity<PageInfoDto> listNotice(@RequestParam(value = "page", defaultValue = "1")int page,
                                                  @RequestParam(value = "size", defaultValue = "20")int size){

        return ResponseEntity.ok(noticeService.listNotice(page - 1, size));
    }
}
