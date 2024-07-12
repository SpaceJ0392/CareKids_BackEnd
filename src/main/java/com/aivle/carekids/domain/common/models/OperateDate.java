package com.aivle.carekids.domain.common.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@MappedSuperclass
@Getter
public abstract class OperateDate extends OperateTime{
    @Enumerated(EnumType.STRING)
    private DayType dayType;

}
