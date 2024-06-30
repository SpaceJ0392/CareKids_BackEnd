package com.aivle.carekids.domain.place.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlaceCateId implements Serializable {

    private Long placeSubCateId;
    private Long placeId;
}
