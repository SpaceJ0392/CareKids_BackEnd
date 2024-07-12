package com.aivle.carekids.domain.common.models;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@MappedSuperclass
@Getter
public abstract class OperateTime {

    private LocalTime startTime;
    private LocalTime endTime;

}
