package com.aivle.carekids.domain.common.service;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.HomeDto;
import com.aivle.carekids.domain.common.repository.AgeTagRepository;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyMainListDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyRegionAgeDto;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyRepository;
import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.notice.repository.NoticeRepository;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoListDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoMainListDto;
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

    private final AgeTagRepository ageTagRepository;
    private final RegionRepository regionRepository;
    private final PlayInfoRepository playInfoRepository;
    private final NoticeRepository noticeRepository;
    private final KidsPolicyRepository kidsPolicyRepository;
    private final ModelMapper dtoModelMapper;

    public HomeDto displayHomeGuest() {
        // 랜덤 지역, 랜덤 연령대 데이터 5개 추출

        AgeTagDto randomAgeTagInPlayInfo = playInfoRepository.findRandomAgeTagInPlayInfo();
        List<PlayInfoListDto> playInfoList = playInfoRepository.findTop4ByAgeTagOrderByUpdatedAtDesc(randomAgeTagInPlayInfo.getAgeTagid())
                .stream().map(p -> dtoModelMapper.map(p, PlayInfoListDto.class)).toList();

        PlayInfoMainListDto playInfoMainList = new PlayInfoMainListDto(playInfoList, randomAgeTagInPlayInfo);


        KidsPolicyRegionAgeDto randomRegionAndAgeTag = kidsPolicyRepository.findRandomRegionAndAgeTag();
        List<KidsPolicyListDto> kidsPolicyList = kidsPolicyRepository.findTop5ByRegionAndAgeTagOrderByUpdatedAtDesc(
                randomRegionAndAgeTag.getRegionDto().getRegionId(), randomRegionAndAgeTag.getAgeTagDto().getAgeTagid()
                ).stream()
                .map(k -> dtoModelMapper.map(k, KidsPolicyListDto.class)).toList();

        KidsPolicyMainListDto kidsPolicyMainListDto = new KidsPolicyMainListDto(
                randomRegionAndAgeTag.getRegionDto(), randomRegionAndAgeTag.getAgeTagDto(),
                kidsPolicyList
        );


        List<NoticeDto> noticeList = noticeRepository.findTop5ByOrderByUpdatedAtDesc().stream()
                .map(n -> dtoModelMapper.map(n, NoticeDto.class)).toList();

        return new HomeDto(kidsPolicyMainListDto, noticeList , playInfoMainList);
    }

    // TODO - 로그인 상태일 때는, 지역 및 연령대 고려, 리스트 필요
}
