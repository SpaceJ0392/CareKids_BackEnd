package com.aivle.carekids.domain.place.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaceCate {

    @EmbeddedId
    private PlaceCateId placeCateId;

    @MapsId("placeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @MapsId("placeSubCateId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_subcate_id")
    private PlaceSubcate placeSubcate;

}
