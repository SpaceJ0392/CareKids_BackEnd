package com.aivle.carekids.domain.common.service;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.HomeDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyMainListDto;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyRepository;
import com.aivle.carekids.domain.notice.repository.NoticeRepository;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoListDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoMainListDto;
import com.aivle.carekids.domain.playInfo.repository.PlayInfoRepository;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final UsersRepository usersRepository;

    private final RegionRepository regionRepository;
    private final PlayInfoRepository playInfoRepository;
    private final NoticeRepository noticeRepository;
    private final KidsPolicyRepository kidsPolicyRepository;

    private final ModelMapper dtoModelMapper;
    private final ObjectMapper objectMapper;

    public HomeDto displayHomeGuest() {

        // 랜덤 나이대 놀이 정보 4개 추출
        AgeTagDto randomAgeTagInPlayInfo = playInfoRepository.findRandomAgeTagInPlayInfo();
        List<PlayInfoListDto> playInfoList = playInfoRepository.findTop4ByAgeTagOrderByUpdatedAtDesc(randomAgeTagInPlayInfo.getAgeTagId());
        //objectMapper.enable(SerializationFeature.WRITE_NL)


        PlayInfoMainListDto playInfoMainList = new PlayInfoMainListDto(playInfoList, randomAgeTagInPlayInfo);

        // 서울 전체 육아 정보 4개 추출
        List<KidsPolicyListDto> kidsPolicyList = kidsPolicyRepository.findTop4ByRegionOrderByUpdatedAtDesc(null);

        List<RegionDto> regionList = new ArrayList<>();
        RegionDto regionBase = dtoModelMapper.map(regionRepository.findByRegionName("전체"), RegionDto.class);
        regionList.add(regionBase);
        KidsPolicyMainListDto kidsPolicyMainListDto = new KidsPolicyMainListDto(
                regionList, kidsPolicyList
        );


        return new HomeDto(kidsPolicyMainListDto, playInfoMainList);

    }

    public HomeDto displayHomeUser(Long usersId) {
        Optional<UsersDetailDto> users = usersRepository.findUsersDetailWithRegionAndKids(usersId);

        if (users.isEmpty()){ return null; }

        RegionDto usersRegion = users.get().getUsersRegion();
        List<AgeTagDto> kidsAgeTags = users.get().getUsersAgeTagDtos();

        Random random = new Random();
        AgeTagDto customAgeTag = kidsAgeTags.get(random.nextInt(kidsAgeTags.size()));

        // 여러 아이가 있는 경우, 랜덤으로 여러 자녀의 연령대 중 pick 1
        List<PlayInfoListDto> playInfoList = playInfoRepository.findTop4ByAgeTagOrderByUpdatedAtDesc(customAgeTag.getAgeTagId());
        PlayInfoMainListDto playInfoMainList = new PlayInfoMainListDto(playInfoList, customAgeTag);

        // 육아 정책 리스트 (서울 전체)
        List<KidsPolicyListDto> kidsPolicyList = kidsPolicyRepository.findTop4ByRegionOrderByUpdatedAtDesc(usersRegion.getRegionId());

        List<RegionDto> regionList = new ArrayList<>();
        regionList.add(dtoModelMapper.map(regionRepository.findByRegionName("전체"), RegionDto.class));
        regionList.add(usersRegion);

        KidsPolicyMainListDto kidsPolicyMainListDto = new KidsPolicyMainListDto(regionList, kidsPolicyList);

        return new HomeDto(kidsPolicyMainListDto, playInfoMainList);
    }
}
