package com.aivle.carekids.domain.common.dto;

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



    public OperateTimeDto(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public OperateTimeDto(String dayType, LocalTime startTime, LocalTime endTime) {
        this.dayType = dayType;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
