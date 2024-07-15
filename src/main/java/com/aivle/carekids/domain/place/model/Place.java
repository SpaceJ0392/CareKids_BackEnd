package com.aivle.carekids.domain.place.model;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
import com.aivle.carekids.domain.hospital.model.HospitalType;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyType;
import com.aivle.carekids.domain.place.dto.PlaceDetailDto;
import com.aivle.carekids.domain.playInfo.model.PlayInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE place SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Place extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @Column(length = 100, nullable = false)
    private String placeName;

    @Column(length = 500)
    private String placeImgUrl;

    @Column(length = 100)
    private String placeAddress;

    private String placeNewAddress;

    @Column(precision = 10, scale = 6)
    private BigDecimal placeX;

    @Column(precision = 10, scale = 6)
    private BigDecimal placeY;

    @Column(length = 70)
    private String placePhone;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @Enumerated(EnumType.STRING)
    private ParkingType placeParking;

    @Enumerated(EnumType.STRING)
    private FreeType placeFree;

    private String placeOperateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    private boolean deleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private List<PlaceCate> placeCates = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private List<PlaceKeyword> placeKeywords = new ArrayList<>();

    @Builder
    public Place(Long placeId, String placeName, String placeImgUrl, String placeAddress, String placeNewAddress, String placePhone, PlaceType placeType, ParkingType placeParking, FreeType placeFree, String placeOperateTime) {
        this.placeName = placeName;
        this.placeImgUrl = placeImgUrl;
        this.placeAddress = placeAddress;
        this.placeNewAddress = placeNewAddress;
        this.placePhone = placePhone;
        this.placeType = placeType;
        this.placeParking = placeParking;
        this.placeFree = placeFree;
        this.placeOperateTime = placeOperateTime;
    }

    public static Place createNewPlace(PlaceDetailDto placeDetailDto) {
        PlaceType placeType = PlaceType.fromPlaceTypeString(placeDetailDto.getPlaceType());
        ParkingType parkingType = ParkingType.fromParkingTypeString(placeDetailDto.getPlaceParking());
        FreeType freeType = FreeType.fromFreeTypeString(placeDetailDto.getPlaceFree());

        return Place.builder()
                .placeName(placeDetailDto.getPlaceName())
                .placeImgUrl(placeDetailDto.getPlaceImgUrl())
                .placeAddress(placeDetailDto.getPlaceAddress())
                .placeNewAddress(placeDetailDto.getPlaceNewAddress())
                .placePhone(placeDetailDto.getPlacePhone())
                .placeType(placeType)
                .placeParking(parkingType)
                .placeFree(freeType)
                .placeOperateTime(placeDetailDto.getPlaceOperateTime())
                .build();
    }

    public void setRegionInfo(Region targetRegion) {
        this.region = targetRegion;
        targetRegion.getPlace().add(this);
    }

    public void updatePlaceInfo(PlaceDetailDto placeDetailDto) {

        PlaceType placeType = PlaceType.fromPlaceTypeString(placeDetailDto.getPlaceType());
        ParkingType parkingType = ParkingType.fromParkingTypeString(placeDetailDto.getPlaceParking());
        FreeType freeType = FreeType.fromFreeTypeString(placeDetailDto.getPlaceFree());

        this.placeName = placeDetailDto.getPlaceName();
        this.placeImgUrl = placeDetailDto.getPlaceImgUrl();
        this.placeAddress = placeDetailDto.getPlaceAddress();
        this.placeNewAddress = placeDetailDto.getPlaceNewAddress();
        this.placePhone = placeDetailDto.getPlacePhone();
        this.placeType = placeType;
        this.placeParking = parkingType;
        this.placeFree = freeType;
        this.placeOperateTime = placeDetailDto.getPlaceOperateTime();

    }

    public void clearPlaceCates() {
        placeCates.forEach(placeCate -> {
            placeCate.setPlaceCateInfo(null, null);
        });
        this.placeCates.clear();
    }


    public void clearPlaceKeywords() {
        placeKeywords.forEach(placeKeyword -> {
            placeKeyword.setPlaceKeywordInfo(null, null);
        });
        this.placeKeywords.clear();
    }

    public void deletedPlace(boolean deleted){
        this.deleted = deleted;
    }
}
