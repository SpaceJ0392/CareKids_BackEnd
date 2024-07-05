package com.aivle.carekids.domain.hospital.repository;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface HospitalRepositoryCustom {

    RegionDto findRandomRegion();

    Page<HospitalListDto> findHospitalByFilter(Long regionId, Pageable pageable);
}
