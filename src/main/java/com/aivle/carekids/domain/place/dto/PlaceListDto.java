package com.aivle.carekids.domain.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class PlaceListDto {

    private Long placeId;

    private String placeName;

    private String placeImgUrl;

    private String placeAddress;

    private String placeNewAddress;

    private String placeOperateTime;

    private PlaceSubcateDto placeSubcate;

    private PlaceMaincateDto placeMaincate;

    private List<PlaceKeywordDto> placeKeywords;

    public PlaceListDto(Long placeId, String placeName, String placeImgUrl, String placeAddress, String placeNewAddress, String placeOperateTime, PlaceSubcateDto placeSubcate, PlaceMaincateDto placeMaincate) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeImgUrl = placeImgUrl;
        this.placeAddress = placeAddress;
        this.placeNewAddress = placeNewAddress;
        this.placeOperateTime = placeOperateTime;
        this.placeSubcate = placeSubcate;
        this.placeMaincate = placeMaincate;
    }
}
