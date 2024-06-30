package com.aivle.carekids.domain.place.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlaceKeywordId implements Serializable {

    private Long keywordId;
    private Long placeId;
}
