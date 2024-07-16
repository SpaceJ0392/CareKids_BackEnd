package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.common.dto.RegionAgeDto;
import com.aivle.carekids.domain.common.dto.SearchRegionAgeTagDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KidsPolicyRepositoryCustom {

    RegionAgeDto findRandomRegionAndAgeTag();

    List<KidsPolicyListDto> findTop4ByRegionOrderByUpdatedAtDesc(Long regionId);

    Page<KidsPolicyListDto> findByRegionAndAgeTagOrderByUpdatedAtDescPage(Long regionId, Pageable pageable);

    Page<KidsPolicyDetailDto> findAllOrderByUpdatedAtDescPage(Pageable pageable);

    KidsPolicyDetailDto findKidsPolicyDetail(Long kidsPolicyId);



    Page<KidsPolicyListDto> searchKidsPolicyByFilter(SearchRegionAgeTagDto searchRegionAgeTagDto, Pageable pageable);
}