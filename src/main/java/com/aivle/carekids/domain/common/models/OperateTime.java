package com.aivle.carekids.domain.common.models;

import jakarta.persistence.MappedSuperclass;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
=======
import lombok.AccessLevel;
>>>>>>> a6a17f72ec938842715e4f3c448d94b1506e4c87
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OperateTime {

    private LocalTime startTime;
    private LocalTime endTime;

    public OperateTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
