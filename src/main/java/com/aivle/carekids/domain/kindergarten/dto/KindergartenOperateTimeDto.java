package com.aivle.carekids.domain.kindergarten.dto;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;

import java.time.LocalTime;

import com.aivle.carekids.domain.common.models.DayType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KindergartenOperateTimeDto extends OperateTimeDto {

    @JsonIgnore
    private Long kindergartenId;

    public KindergartenOperateTimeDto(DayType dayType, LocalTime startTime, LocalTime endTime, Long kindergartenId) {
        super(dayType, startTime, endTime);
        this.kindergartenId = kindergartenId;
    }

}
