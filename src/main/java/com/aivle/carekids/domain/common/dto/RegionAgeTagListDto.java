package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@AllArgsConstructor
public class RegionAgeTagListDto {

    @JsonProperty("age-tag")
    private List<AgeTagDto> ageTag;

    private List<RegionDto> region;

}
