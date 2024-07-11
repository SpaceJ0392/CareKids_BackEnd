package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.place.dto.PlaceListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {
    Page<PlaceListDto> findAllByOrderByUpdatedAtDescByPageByRegion(Long regionId, Pageable pageable);
}
