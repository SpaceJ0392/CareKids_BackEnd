package com.aivle.carekids.domain.place.dto;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.place.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class PlaceDetailDto {

    private Long placeId;

    private String placeName;

    private String placeImgUrl;

    private String placeAddress;

    private String placeNewAddress;

    private String placePhone;

    private PlaceType placeType;

    private ParkingType placeParking;

    private FreeType placeFree;

    private String placeOperateTime;

    private RegionDto regionDto;

    private PlaceSubcateDto placeSubcate;

    private List<PlaceKeywordDto> placeKeywords;

    public PlaceDetailDto(Long placeId, String placeName, String placeImgUrl, String placeAddress, String placeNewAddress, String placePhone, PlaceType placeType, ParkingType placeParking, FreeType placeFree, String placeOperateTime, RegionDto regionDto, PlaceSubcateDto placeSubcate) {
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
        this.regionDto = regionDto;
        this.placeSubcate = placeSubcate;
    }
}
