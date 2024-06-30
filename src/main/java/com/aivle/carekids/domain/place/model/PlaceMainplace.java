package com.aivle.carekids.domain.place.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaceMainplace {

    @EmbeddedId
    private PlaceMainplaceId placeMainplaceId;

    @MapsId("placeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @MapsId("mainplaceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainplace_id")
    private Mainplace mainplace;
}
