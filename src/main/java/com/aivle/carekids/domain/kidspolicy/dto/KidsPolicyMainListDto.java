package com.aivle.carekids.domain.kidspolicy.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class KidsPolicyMainListDto{

    @JsonProperty("region")
    private RegionDto regionDto;

    @JsonProperty("age-tag")
    private AgeTagDto ageTagDto;

    @JsonProperty("kids-policy")
    private List<KidsPolicyListDto> kidsPolicyListDtos;
}
