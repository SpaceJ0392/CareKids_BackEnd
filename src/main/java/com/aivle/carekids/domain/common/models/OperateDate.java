package com.aivle.carekids.domain.common.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OperateDate extends OperateTime{
    @Enumerated(EnumType.STRING)
    protected DayType dayType;

    public OperateDate(DayType dayType, LocalTime startTime, LocalTime endTime) {
        super(startTime, endTime);
        this.dayType = dayType;
    }
}
