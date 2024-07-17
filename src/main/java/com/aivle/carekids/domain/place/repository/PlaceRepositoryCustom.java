package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.common.dto.SearchRegionCateDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenListDto;
import com.aivle.carekids.domain.place.dto.PlaceDetailDto;
import com.aivle.carekids.domain.place.dto.PlaceListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {

    Page<PlaceListDto> findAllByOrderByUpdatedAtDescByPageByRegionMainCate(Long regionId, String maincateName, Pageable pageable);

    Page<PlaceDetailDto> findAllByOrderByUpdatedAtDescByPageByRegionMainCateAdmin(Long regionId, String maincateName, Pageable pageable);

    PlaceDetailDto findPlaceDetail(Long placeId);

    Page<PlaceListDto> searchPlaceByFilter(SearchRegionCateDto searchRegionCateDto, Pageable pageable);

}
