package com.aivle.carekids.domain.common.service;

import com.aivle.carekids.domain.common.dto.HomeDto;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyRepository;
import com.aivle.carekids.domain.notice.repository.NoticeRepository;
import com.aivle.carekids.domain.playInfo.repository.PlayInfoRepository;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final UsersRepository usersRepository;

    private final PlayInfoRepository playInfoRepository;
    private final NoticeRepository noticeRepository;
    private final KidsPolicyRepository kidsPolicyRepository;

    private final ModelMapper dtoModelMapper;

    public HomeDto displayHomeGuest() {
        // 랜덤 지역, 랜덤 연령대 데이터 5개 추출

//        AgeTagDto randomAgeTagInPlayInfo = playInfoRepository.findRandomAgeTagInPlayInfo();
//        List<PlayInfoListDto> playInfoList = playInfoRepository.findTop4ByAgeTagOrderByUpdatedAtDesc(randomAgeTagInPlayInfo.getAgeTagid())
//                .stream().map(p -> dtoModelMapper.map(p, PlayInfoListDto.class)).toList();
//
//        PlayInfoMainListDto playInfoMainList = new PlayInfoMainListDto(playInfoList, randomAgeTagInPlayInfo);
//
//
//        RegionAgeDto randomRegionAndAgeTag = kidsPolicyRepository.findRandomRegionAndAgeTag();
//        List<KidsPolicyListDto> kidsPolicyList = kidsPolicyRepository.findTop5ByRegionAndAgeTagOrderByUpdatedAtDesc(
//                randomRegionAndAgeTag.getRegionDto().getRegionId(), randomRegionAndAgeTag.getAgeTagDto().getAgeTagid()
//                ).stream()
//                .map(k -> dtoModelMapper.map(k, KidsPolicyListDto.class)).toList();
//
//        KidsPolicyMainListDto kidsPolicyMainListDto = new KidsPolicyMainListDto(
//                randomRegionAndAgeTag.getRegionDto(), randomRegionAndAgeTag.getAgeTagDto(),
//                kidsPolicyList
//        );
//
//
//        List<NoticeDto> noticeList = noticeRepository.findTop5ByOrderByUpdatedAtDesc().stream()
//                .map(n -> dtoModelMapper.map(n, NoticeDto.class)).toList();
//
//        return new HomeDto(kidsPolicyMainListDto, noticeList , playInfoMainList);

        return null;
    }

    public HomeDto displayHomeUser(Long usersId) {
//        Optional<UsersDto> users = usersRepository.findUsersDetailWithRegionAndKids(usersId);
//
//        if (users.isEmpty()){ return null; }
//
//        RegionDto usersRegion = users.get().getUsersRegion();
//        List<AgeTagDto> kidsAgeTags = users.get().getUsersAgeTagDtos();
//
//        Random random = new Random();
//        AgeTagDto customAgeTag = kidsAgeTags.get(random.nextInt(kidsAgeTags.size()));
//
//        // 여러 아이가 있는 경우에도, 랜덤으로 여러 자녀의 연령대 중 pick 1
//        List<PlayInfoListDto> playInfoList = playInfoRepository.findTop4ByAgeTagOrderByUpdatedAtDesc(customAgeTag.getAgeTagid())
//                .stream().map(p -> dtoModelMapper.map(p, PlayInfoListDto.class)).toList();
//        PlayInfoMainListDto playInfoMainList = new PlayInfoMainListDto(playInfoList, customAgeTag);
//
//        // 육아 정책 리스트 지역 기준으로...
//        List<KidsPolicyListDto> kidsPolicyList = kidsPolicyRepository.findTop5ByRegionAndAgeTagOrderByUpdatedAtDesc(
//                        usersRegion.getRegionId(), customAgeTag.getAgeTagid()
//                ).stream()
//                .map(k -> dtoModelMapper.map(k, KidsPolicyListDto.class)).toList();
//        KidsPolicyMainListDto kidsPolicyMainListDto = new KidsPolicyMainListDto(usersRegion, customAgeTag, kidsPolicyList);
//
//        // 공지 사항 (그대로)
//        List<NoticeDto> noticeList = noticeRepository.findTop5ByOrderByUpdatedAtDesc().stream()
//                .map(n -> dtoModelMapper.map(n, NoticeDto.class)).toList();
//
//        return new HomeDto(kidsPolicyMainListDto, noticeList, playInfoMainList);

        return null;
    }
}
