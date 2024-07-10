package com.aivle.carekids.domain.hospital.dto;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.models.DayType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
public class HospitalTimeDto extends OperateTimeDto {

    @JsonIgnore
    private Long hospitalId;

    public HospitalTimeDto(DayType dayType, LocalTime startTime, LocalTime endTime, Long hospitalId) {
        super(dayType.getDayType(), startTime, endTime);
        this.hospitalId = hospitalId;
    }
}
