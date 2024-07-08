package com.aivle.carekids.domain.kidspolicy.service;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.dto.SearchRegionAgeTagDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyRepository;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KidsPolicyService {

    private final KidsPolicyRepository kidsPolicyRepository;
    private final UsersRepository usersRepository;

    public PageInfoDto displayKidsPolicyGuest(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KidsPolicyListDto> kidsPolicyList =
                kidsPolicyRepository.findByRegionAndAgeTagOrderByUpdatedAtDescPage(null, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                kidsPolicyList.getTotalPages(),
                kidsPolicyList.getNumber() + 1,
                kidsPolicyList.getSize(),
                kidsPolicyList.getNumberOfElements()
        ), kidsPolicyList.getContent());
    }

    public PageInfoDto displayKidsPolicyUser(Long usersId, int page, int size) {

        Optional<UsersDetailDto> users = usersRepository.findUsersDetailWithRegionAndKids(usersId);
        if (users.isEmpty()){ return null; }

        Pageable pageable = PageRequest.of(page, size);
        RegionDto usersRegion = users.get().getUsersRegion();

        Page<KidsPolicyListDto> kidsPolicyList =
                kidsPolicyRepository.findByRegionAndAgeTagOrderByUpdatedAtDescPage(usersRegion.getRegionId(), pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                kidsPolicyList.getTotalPages(),
                kidsPolicyList.getNumber() + 1,
                kidsPolicyList.getSize(),
                kidsPolicyList.getNumberOfElements()
        ), kidsPolicyList.getContent());
    }

    public KidsPolicyDetailDto kidsPolicyDetail(Long kidsPolicyId) {
        if (!kidsPolicyRepository.existsById(kidsPolicyId)) { return null; }
        return kidsPolicyRepository.findKidsPolicyDetail(kidsPolicyId);
    }

    public PageInfoDto searchKidsPolicy(SearchRegionAgeTagDto searchRegionAgeTagDto, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<KidsPolicyListDto> searchHospitalListDtos = kidsPolicyRepository.searchKidsPolicyByFilter(searchRegionAgeTagDto, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                searchHospitalListDtos.getTotalPages(),
                searchHospitalListDtos.getNumber() + 1,
                searchHospitalListDtos.getSize(),
                searchHospitalListDtos.getNumberOfElements()
        ), searchHospitalListDtos.getContent());
    }
}
