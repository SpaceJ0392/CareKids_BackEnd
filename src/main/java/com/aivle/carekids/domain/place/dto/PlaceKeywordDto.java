package com.aivle.carekids.domain.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PlaceKeywordDto {

    private Long placeId;

    private String keywordName;

    public PlaceKeywordDto(Long placeId, String keywordName) {
        this.placeId = placeId;
        this.keywordName = keywordName;
    }
}
