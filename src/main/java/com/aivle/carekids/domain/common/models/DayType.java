package com.aivle.carekids.domain.common.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayType {

    WEEKDAY("평일"), WEEKEND("주말");

    private final String dayType;

    public static DayType fromDayTypeString(String dayType){
        for (DayType type : DayType.values()) {
            if (type.getDayType().equals(dayType)) { return type; }
        }
        throw new IllegalArgumentException("No matching DayType for description: " + dayType);
    }
}
