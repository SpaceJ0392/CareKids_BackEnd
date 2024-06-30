package com.aivle.carekids.domain.common.service;

import com.aivle.carekids.domain.common.dto.HomeDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDto;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyRepository;
import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.notice.repository.NoticeRepository;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDto;
import com.aivle.carekids.domain.playInfo.repository.PlayInfoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final PlayInfoRepository playInfoRepository;
    private final NoticeRepository noticeRepository;
    private final KidsPolicyRepository kidsPolicyRepository;
    private final ModelMapper dtoModelMapper;

    public HomeDto displayHome() {
        // TODO - 로그인 상태일 때는, 지역 및 연령대 고려, 리스트 필요

        List<PlayInfoDto> playInfoList = playInfoRepository.findTop5ByOrderByUpdatedAtDesc().stream()
                .map(p -> dtoModelMapper.map(p, PlayInfoDto.class)).toList();

        List<KidsPolicyDto> kidsPolicyList = kidsPolicyRepository.findTop5ByOrderByUpdatedAtDesc().stream()
                .map(k -> dtoModelMapper.map(k, KidsPolicyDto.class)).toList();

        List<NoticeDto> noticeList = noticeRepository.findTop5ByOrderByUpdatedAtDesc().stream()
                .map(n -> dtoModelMapper.map(n, NoticeDto.class)).toList();

        return new HomeDto(kidsPolicyList, noticeList ,playInfoList);
    }
}
