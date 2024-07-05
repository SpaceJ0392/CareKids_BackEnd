package com.aivle.carekids.domain.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RegionAgeDto {

    private RegionDto regionDto;

    private AgeTagDto ageTagDto;

    @QueryProjection
    public RegionAgeDto(RegionDto regionDto, AgeTagDto ageTagDto) {
        this.regionDto = regionDto;
        this.ageTagDto = ageTagDto;
    }
}
