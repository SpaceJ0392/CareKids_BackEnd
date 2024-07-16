package com.aivle.carekids.domain.common.dto;

import com.aivle.carekids.domain.place.dto.PlaceMaincateDto;
import com.aivle.carekids.domain.place.dto.PlaceSubcateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class RegionMaincateListDto {
    private List<RegionDto> region;
    private List<PlaceMaincateDto> categories;

    public RegionMaincateListDto(List<RegionDto> region, List<PlaceMaincateDto> categories) {
        this.region = region;
        this.categories = categories;
    }
}
