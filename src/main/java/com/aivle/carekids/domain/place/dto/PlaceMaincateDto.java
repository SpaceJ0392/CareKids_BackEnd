package com.aivle.carekids.domain.place.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceMaincateDto {

    @JsonProperty("id")
    private Long placeMaincateId;

    @JsonProperty("name")
    private String placeMaincateName;

    public PlaceMaincateDto(Long placeMaincateId, String placeMaincateName) {
        this.placeMaincateId = placeMaincateId;
        this.placeMaincateName = placeMaincateName;
    }
}
