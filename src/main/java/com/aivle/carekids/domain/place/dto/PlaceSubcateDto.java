package com.aivle.carekids.domain.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PlaceSubcateDto {

    private String placeSubcateName;

    public PlaceSubcateDto(String placeSubcateName) {
        this.placeSubcateName = placeSubcateName;
    }
}
