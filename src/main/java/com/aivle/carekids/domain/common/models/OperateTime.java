package com.aivle.carekids.domain.common.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalTime;

@MappedSuperclass
@Getter
public abstract class OperateTime {

    private LocalTime startTime;
    private LocalTime endTime;
}
