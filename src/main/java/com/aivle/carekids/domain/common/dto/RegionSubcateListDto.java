package com.aivle.carekids.domain.common.dto;

import com.aivle.carekids.domain.place.dto.PlaceSubcateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegionSubcateListDto {

    private List<RegionDto> region;

    private List<PlaceSubcateDto> categories;

    public RegionSubcateListDto(List<RegionDto> region, List<PlaceSubcateDto> categories) {
        this.region = region;
        this.categories = categories;
    }
}
