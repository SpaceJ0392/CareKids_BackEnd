package com.aivle.carekids.domain.place.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParkingType {

    NO("X"), YES("O"), NO_INFO("정보 없음");

    private final String parkingType;

    public static ParkingType fromParkingTypeString(String parkingType) {
        for (ParkingType type : ParkingType.values()) {
            if (type.getParkingType().equals(parkingType)) { return type; }
        }
        throw new IllegalArgumentException("No matching ParkingType for description: " + parkingType);
    }
}
