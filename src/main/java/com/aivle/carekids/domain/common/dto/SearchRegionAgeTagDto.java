package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SearchRegionAgeTagDto {

    @JsonProperty("query")
    private String query;

    @JsonProperty("region")
    private RegionDto regionDto;

    @JsonProperty("age-tag")
    private AgeTagDto ageTagDto;
}
