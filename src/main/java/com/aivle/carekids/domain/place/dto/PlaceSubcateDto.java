package com.aivle.carekids.domain.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PlaceSubcateDto {

    private Long placeSubcateId;

    private String placeSubcateName;

    public PlaceSubcateDto(Long placeSubcateId, String placeSubcateName) {
        this.placeSubcateId = placeSubcateId;
        this.placeSubcateName = placeSubcateName;
    }
}
