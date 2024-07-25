package com.aivle.carekids.domain.kindergarten.repository;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface KindergartenRepositoryCustom {

    RegionDto findRandomRegion();

    Page<KindergartenListDto> findAllByOrderByUpdatedAtDescByPageByRegion(Long regionId, Pageable pageable);

    KindergartenDetailDto findKindergartenDetail(Long kindergartenId);

    Page<KindergartenListDto> searchKindergartenByFilter(SearchRegionDto searchRegionDto, Pageable pageable);
}
