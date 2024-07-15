package com.aivle.carekids.domain.place.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PlaceKeywordDto {

    @JsonIgnore
    private Long placeId;

    private Long keywordId;

    private String keywordName;

    public PlaceKeywordDto(Long placeId, Long keywordId, String keywordName) {
        this.placeId = placeId;
        this.keywordId = keywordId;
        this.keywordName = keywordName;
    }
}
