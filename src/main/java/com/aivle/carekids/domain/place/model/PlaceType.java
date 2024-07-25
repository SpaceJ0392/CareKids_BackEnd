package com.aivle.carekids.domain.place.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceType {

    INNER("실내"), OUTER("실외"), NO_INFO("정보 없음");

    private final String placeType;

    public static PlaceType fromPlaceTypeString(String placeType) {
        for (PlaceType type : PlaceType.values()) {
            if (type.getPlaceType().equals(placeType)) { return type; }
        }
        throw new IllegalArgumentException("No matching PlaceType for description: " + placeType);
    }
}