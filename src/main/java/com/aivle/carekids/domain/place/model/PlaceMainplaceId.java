package com.aivle.carekids.domain.place.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlaceMainplaceId implements Serializable {

    private Long mainplaceId;
    private Long placeId;
}
