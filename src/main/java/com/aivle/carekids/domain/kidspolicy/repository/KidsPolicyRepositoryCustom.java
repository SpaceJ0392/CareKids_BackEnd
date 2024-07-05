package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.aivle.carekids.domain.common.dto.RegionAgeDto;

import java.util.List;

public interface KidsPolicyRepositoryCustom {

    RegionAgeDto findRandomRegionAndAgeTag();

    List<KidsPolicyListDto> findTop5ByRegionAndAgeTagOrderByUpdatedAtDesc(Long ageTagId, Long regionId);
}
