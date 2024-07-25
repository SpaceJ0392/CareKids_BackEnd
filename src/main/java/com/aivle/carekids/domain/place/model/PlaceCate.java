package com.aivle.carekids.domain.place.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaceCate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeCateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_subcate_id")
    private PlaceSubcate placeSubcate;

    @Builder
    public PlaceCate(Place place, PlaceSubcate placeSubcate) {
        this.place = place;
        this.placeSubcate = placeSubcate;
    }

    //* 사용자 정의 메소드 *//
    public static PlaceCate createNewPlaceCate(Place place, PlaceSubcate placeSubcate) {

        return PlaceCate.builder()
                .place(place)
                .placeSubcate(placeSubcate)
                .build();
    }

    public void setPlaceCateInfo(Place place, PlaceSubcate placeSubcate){

        if (place == null || placeSubcate == null){
            this.place = null;
            this.placeSubcate = null;
            return;
        }

        this.place = place;
        place.getPlaceCates().add(this);

        this.placeSubcate = placeSubcate;
        placeSubcate.getPlaceCates().add(this);
    }

}
