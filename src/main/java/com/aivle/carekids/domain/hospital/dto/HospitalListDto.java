package com.aivle.carekids.domain.hospital.dto;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.models.DayOfWeek;
import com.aivle.carekids.domain.hospital.model.HospitalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
@NoArgsConstructor
public class HospitalListDto {

    private Long hospitalId;

    private String hospitalName;

    private HospitalType hospitalType;

    private RegionDto hospitalRegion;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    public HospitalListDto(Long hospitalId, String hospitalName, HospitalType hospitalType,
                           RegionDto hospitalRegion, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {

        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType;
        this.hospitalRegion = hospitalRegion;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
