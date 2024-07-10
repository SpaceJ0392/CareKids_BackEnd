package com.aivle.carekids.domain.common.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayType {
    WEEKDAY("평일"), WEEKEND("주말");

    private final String dayType;
}
