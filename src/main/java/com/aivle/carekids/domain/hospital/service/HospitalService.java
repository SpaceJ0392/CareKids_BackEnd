package com.aivle.carekids.domain.hospital.service;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import com.aivle.carekids.domain.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalListDto displayHospitalGuest(Pageable pageable) {

        RegionDto randomRegion = hospitalRepository.findRandomRegion();
        Page<HospitalListDto> hospitalByRegion = hospitalRepository.findHospitalByFilter(randomRegion.getRegionId(), pageable);

        return null;
    }

    public HospitalListDto displayHospitalUser(Long usersId, Pageable pageable) {
        return null;
    }
}
