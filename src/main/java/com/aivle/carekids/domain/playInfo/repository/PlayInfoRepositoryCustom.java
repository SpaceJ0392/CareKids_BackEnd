package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.SearchAgeTagDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDetailDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayInfoRepositoryCustom {

    AgeTagDto findRandomAgeTagInPlayInfo();

    List<PlayInfoListDto> findTop4ByAgeTagOrderByUpdatedAtDesc(Long ageTagId);

    Page<PlayInfoListDto> findAllByOrderByUpdatedAtDescByPageByAge(Long ageTagId, Pageable pageable);

    PlayInfoDetailDto findPlayInfoDetail(Long playInfoId);

    Page<PlayInfoListDto> searchPlayInfoByFilter(SearchAgeTagDto searchAgeTagDto, Pageable pageable);


}
