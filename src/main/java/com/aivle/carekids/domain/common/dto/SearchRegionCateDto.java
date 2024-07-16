package com.aivle.carekids.domain.common.dto;

import com.aivle.carekids.domain.place.dto.PlaceMaincateDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SearchRegionCateDto {
    @JsonProperty("query")
    private String query;

    @JsonProperty("region")
    private RegionDto regionDto;

    @JsonProperty("maincate")
    private PlaceMaincateDto maincateDto;
}
