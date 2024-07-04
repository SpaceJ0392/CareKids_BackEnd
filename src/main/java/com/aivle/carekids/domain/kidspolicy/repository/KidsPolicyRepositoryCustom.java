package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyRegionAgeDto;

import java.util.List;

public interface KidsPolicyRepositoryCustom {

    KidsPolicyRegionAgeDto findRandomRegionAndAgeTag();

    List<KidsPolicyListDto> findTop5ByRegionAndAgeTagOrderByUpdatedAtDesc(Long ageTagId, Long regionId);
}
