package com.aivle.carekids.domain.notice.service;

import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.notice.dto.PageInfoDto;
import com.aivle.carekids.domain.notice.models.Notice;
import com.aivle.carekids.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper dtoModelMapper;

    public PageInfoDto listNotice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Notice> noticePage = noticeRepository.findAllByOrderByUpdatedAtDesc(pageable);
        List<NoticeDto> noticeDtoList = noticePage.map(notice
                -> new NoticeDto(
                notice.getNoticeId(), notice.getNoticeTitle(), notice.getNoticeText(),
                notice.getCreatedAt(), notice.getUpdatedAt()
        )).toList();

        return new PageInfoDto(new PageInfoDto.PageInfo(noticePage.getTotalPages(), page, size), noticeDtoList);
    }
}
