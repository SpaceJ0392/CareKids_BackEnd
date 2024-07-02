package com.aivle.carekids.domain.common.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class OperateDate extends OperateTime{
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
}
