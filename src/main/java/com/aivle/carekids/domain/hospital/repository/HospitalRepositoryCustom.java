package com.aivle.carekids.domain.hospital.repository;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface HospitalRepositoryCustom {

    RegionDto findRandomRegion();

    Page<HospitalListDto> findAllByOrderByUpdatedAtDescByPageByRegion(Long regionId, Pageable pageable);

    Page<HospitalListDto> findHospitalByFilter(Long regionId, LocalTime now, DayOfWeek today, Pageable pageable);

    HospitalDetailDto findHospialDetail(Long hospitalId);

    Page<HospitalListDto> searchHospitalByFilter(SearchRegionDto searchRegionDto, Pageable pageable);
}