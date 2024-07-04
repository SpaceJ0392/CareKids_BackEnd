package com.aivle.carekids.domain.notice.service;

import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.notice.dto.NoticeListDto;
import com.aivle.carekids.domain.notice.dto.PageInfoDto;
import com.aivle.carekids.domain.notice.models.Notice;
import com.aivle.carekids.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public PageInfoDto listNotice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Notice> noticePage = noticeRepository.findAllByOrderByUpdatedAtDesc(pageable);
        List<NoticeListDto> noticeListDtoList = noticePage.map(notice
                -> new NoticeListDto(
                notice.getNoticeId(), notice.getNoticeTitle(), notice.getCreatedAt(), notice.getUpdatedAt()
        )).toList();

        return new PageInfoDto(new PageInfoDto.PageInfo(noticePage.getTotalPages(), page + 1, size), noticeListDtoList);
    }

    public ResponseEntity<?> noticeDetail(Long id) {

        NoticeDto noticeDto = noticeRepository.findById(id)
                .map(n -> new NoticeDto(n.getNoticeId(), n.getNoticeTitle(), n.getNoticeText(),
                n.getNoticeImgUrl(), n.getCreatedAt(), n.getUpdatedAt())).orElse(null);

        if (noticeDto != null){
            return ResponseEntity.ok(noticeDto);
        }

        return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 ID 입니다."));
    }
}
