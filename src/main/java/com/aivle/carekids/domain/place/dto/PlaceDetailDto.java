package com.aivle.carekids.domain.place.dto;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.place.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class PlaceDetailDto {

    @JsonProperty("id")
    private Long placeId;

    @JsonProperty("name")
    private String placeName;

    @JsonProperty("img-url")
    private String placeImgUrl;

    @JsonProperty("address")
    private String placeAddress;

    @JsonProperty("new-address")
    private String placeNewAddress;

    @JsonProperty("phone")
    private String placePhone;

    @JsonProperty("type")
    private String placeType;

    @JsonProperty("parking-type")
    private String placeParking;

    @JsonProperty("is-free")
    private String placeFree;

    @JsonProperty("operate-time")
    private String placeOperateTime;

    @JsonProperty("region")
    private RegionDto region;

    @JsonProperty("subcate")
    private PlaceSubcateDto placeSubcate;

    @JsonProperty("maincate")
    private PlaceMaincateDto placeMaincate;

    @JsonProperty("keywords")
    private List<PlaceKeywordDto> placeKeywords;

    public PlaceDetailDto(Long placeId, String placeName, String placeImgUrl, String placeAddress, String placeNewAddress, String placePhone, String placeType, String placeParking, String placeFree, String placeOperateTime, RegionDto region, PlaceSubcateDto placeSubcate, PlaceMaincateDto placeMaincate) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeImgUrl = placeImgUrl;
        this.placeAddress = placeAddress;
        this.placeNewAddress = placeNewAddress;
        this.placePhone = placePhone;
        this.placeType = placeType;
        this.placeParking = placeParking;
        this.placeFree = placeFree;
        this.placeOperateTime = placeOperateTime;
        this.region = region;
        this.placeSubcate = placeSubcate;
        this.placeMaincate = placeMaincate;
    }
}
