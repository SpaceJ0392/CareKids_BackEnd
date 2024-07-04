package com.aivle.carekids.domain.kidspolicy.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class KidsPolicyRegionAgeDto {

    private KidsPolicyListDto kidsPolicyListDto;

    private RegionDto regionDto;

    private AgeTagDto ageTagDto;

    @QueryProjection
    public KidsPolicyRegionAgeDto(KidsPolicyListDto kidsPolicyListDto, RegionDto regionDto, AgeTagDto ageTagDto) {
        this.kidsPolicyListDto = kidsPolicyListDto;
        this.regionDto = regionDto;
        this.ageTagDto = ageTagDto;
    }
}
