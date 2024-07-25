package com.aivle.carekids.domain.kindergarten.service;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.RegionDto;

import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenListDto;
import com.aivle.carekids.domain.kindergarten.repository.KindergartenRepository;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KindergartenService {

    private final KindergartenRepository kindergartenRepository;
    private final UsersRepository usersRepository;
    private final RegionRepository regionRepository;
    private final ModelMapper dtoModelMapper;


    public PageInfoDto displayKindergartenGuest(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        RegionDto regionDto = dtoModelMapper.map(regionRepository.findByRegionName("전체"), RegionDto.class);
        Page<KindergartenListDto> kindergartenPage = kindergartenRepository.findAllByOrderByUpdatedAtDescByPageByRegion(null, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                kindergartenPage.getTotalPages(),
                kindergartenPage.getNumber() + 1,
                kindergartenPage.getSize(),
                kindergartenPage.getNumberOfElements()
        ), regionDto, null, kindergartenPage.getContent());
    }


    public PageInfoDto displayKindergartenUser(Long usersId, int page, int size) {

        Optional<UsersDetailDto> users = usersRepository.findUsersDetailWithRegionAndKids(usersId);
        if (users.isEmpty()){ return null; }

        Pageable pageable = PageRequest.of(page, size);
        RegionDto usersRegion = users.get().getUsersRegion();

        Page<KindergartenListDto> kindergartenPage = kindergartenRepository.findAllByOrderByUpdatedAtDescByPageByRegion(usersRegion.getRegionId(), pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                kindergartenPage.getTotalPages(),
                kindergartenPage.getNumber() + 1,
                kindergartenPage.getSize(),
                kindergartenPage.getNumberOfElements()
        ), users.get().getUsersRegion(), null, kindergartenPage.getContent());
    }


    public KindergartenDetailDto kindergartenDetail(Long kindergartenId) {

        if (!kindergartenRepository.existsById(kindergartenId)) { return null; }
        return kindergartenRepository.findKindergartenDetail(kindergartenId);
    }


    public PageInfoDto searchKindergarten(SearchRegionDto searchRegionDto, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<KindergartenListDto> searchKindergartenListDtos = kindergartenRepository.searchKindergartenByFilter(searchRegionDto, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                searchKindergartenListDtos.getTotalPages(),
                searchKindergartenListDtos.getNumber() + 1,
                searchKindergartenListDtos.getSize(),
                searchKindergartenListDtos.getNumberOfElements()
        ), searchRegionDto.getRegionDto(), null, searchKindergartenListDtos.getContent());
    }
}
