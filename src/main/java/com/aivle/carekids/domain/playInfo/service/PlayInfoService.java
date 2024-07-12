package com.aivle.carekids.domain.playInfo.service;

import com.aivle.carekids.domain.common.dto.*;
import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.repository.AgeTagRepository;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenListDto;
import com.aivle.carekids.domain.kindergarten.repository.KindergartenRepository;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDetailDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoListDto;
import com.aivle.carekids.domain.playInfo.repository.PlayInfoRepository;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PlayInfoService {

    private final PlayInfoRepository playInfoRepository;
    private final UsersRepository usersRepository;
    private final AgeTagRepository ageTagRepository;
    private final Random random = new Random();

    private final ModelMapper dtoModelMapper;

    public PageInfoDto displayPlayInfoGuest(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        AgeTagDto ageTagDto = dtoModelMapper.map(ageTagRepository.findByAgeTagName("전체(12세 이하)"), AgeTagDto.class);
        Page<PlayInfoListDto> playInfoPage = playInfoRepository.findAllByOrderByUpdatedAtDescByPageByAge(null, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                playInfoPage.getTotalPages(),
                playInfoPage.getNumber() + 1,
                playInfoPage.getSize(),
                playInfoPage.getNumberOfElements()
        ), null, ageTagDto, playInfoPage.getContent());
    }

    public PageInfoDto displayPlayInfoUser(Long usersId, int page, int size) {

        Optional<UsersDetailDto> users = usersRepository.findUsersDetailWithRegionAndKids(usersId);
        if (users.isEmpty()){ return null; }

        Pageable pageable = PageRequest.of(page, size);
        List<AgeTagDto> usersKidsAges = users.get().getUsersAgeTagDtos();
        int index = random.nextInt(usersKidsAges.size());
        AgeTagDto randAgeTag = usersKidsAges.get(index);

        Page<PlayInfoListDto> playInfoPage = playInfoRepository.findAllByOrderByUpdatedAtDescByPageByAge(randAgeTag.getAgeTagId(), pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                playInfoPage.getTotalPages(),
                playInfoPage.getNumber() + 1,
                playInfoPage.getSize(),
                playInfoPage.getNumberOfElements()
        ), null, randAgeTag, playInfoPage.getContent());
    }

    public PlayInfoDetailDto playInfoDetail(Long playInfoId) {

        if (!playInfoRepository.existsById(playInfoId)) { return null; }
        return playInfoRepository.findPlayInfoDetail(playInfoId);
    }

    public PageInfoDto searchPlayInfo(SearchAgeTagDto searchAgeTagDto, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PlayInfoListDto> searchPlayInfoListDtos = playInfoRepository.searchPlayInfoByFilter(searchAgeTagDto, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                searchPlayInfoListDtos.getTotalPages(),
                searchPlayInfoListDtos.getNumber() + 1,
                searchPlayInfoListDtos.getSize(),
                searchPlayInfoListDtos.getNumberOfElements()
        ), null, searchAgeTagDto.getAgeTagDto(), searchPlayInfoListDtos.getContent());
    }
}
