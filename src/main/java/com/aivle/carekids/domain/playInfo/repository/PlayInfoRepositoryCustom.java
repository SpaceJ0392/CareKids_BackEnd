package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoListDto;

import java.util.List;

public interface PlayInfoRepositoryCustom {

    AgeTagDto findRandomAgeTagInPlayInfo();

    List<PlayInfoListDto> findTop4ByAgeTagOrderByUpdatedAtDesc(Long ageTagId);
}
