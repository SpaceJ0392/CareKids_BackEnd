package com.aivle.carekids.domain.hospital.service;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import com.aivle.carekids.domain.hospital.repository.HospitalRepository;
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
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final UsersRepository usersRepository;

    public com.aivle.carekids.domain.common.dto.PageInfoDto displayHospitalGuest(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<HospitalListDto> hospitalPage = hospitalRepository.findAllByOrderByUpdatedAtDescByPageByRegion(null, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                hospitalPage.getTotalPages(),
                hospitalPage.getNumber() + 1,
                hospitalPage.getSize(),
                hospitalPage.getNumberOfElements()
        ), hospitalPage.getContent());

    }

    public PageInfoDto displayHospitalUser(Long usersId, int page, int size) {

        Optional<UsersDetailDto> users = usersRepository.findUsersDetailWithRegionAndKids(usersId);
        if (users.isEmpty()){ return null; }

        Pageable pageable = PageRequest.of(page, size);
        RegionDto usersRegion = users.get().getUsersRegion();

        Page<HospitalListDto> hospitalPage = hospitalRepository.findAllByOrderByUpdatedAtDescByPageByRegion(usersRegion.getRegionId(), pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                hospitalPage.getTotalPages(),
                hospitalPage.getNumber() + 1,
                hospitalPage.getSize(),
                hospitalPage.getNumberOfElements()
        ), hospitalPage.getContent());
    }

    public HospitalDetailDto hospitalDetail(Long hospitalId) {

        if (!hospitalRepository.existsById(hospitalId)) { return null; }
        return hospitalRepository.findHospialDetail(hospitalId);
    }

    public PageInfoDto searchHospital(SearchRegionDto searchRegionDto, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HospitalListDto> searchHospitalListDtos = hospitalRepository.searchHospitalByFilter(searchRegionDto, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                searchHospitalListDtos.getTotalPages(),
                searchHospitalListDtos.getNumber() + 1,
                searchHospitalListDtos.getSize(),
                searchHospitalListDtos.getNumberOfElements()
        ), searchHospitalListDtos.getContent());
    }
}
