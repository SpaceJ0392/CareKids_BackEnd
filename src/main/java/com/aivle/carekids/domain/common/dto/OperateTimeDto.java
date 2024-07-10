package com.aivle.carekids.domain.common.dto;

import com.aivle.carekids.domain.common.models.DayType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
@NoArgsConstructor
public class OperateTimeDto {

    @JsonProperty("operation-day")
    private String dayType;

    private LocalTime startTime;

    private LocalTime endTime;

    @JsonIgnore
    private Long hospitalId;

    public OperateTimeDto(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public OperateTimeDto(DayType dayType, LocalTime startTime, LocalTime endTime, Long hospitalId) {
        this.dayType = dayType.getDayType();
        this.startTime = startTime;
        this.endTime = endTime;
        this.hospitalId = hospitalId;
    }
}
