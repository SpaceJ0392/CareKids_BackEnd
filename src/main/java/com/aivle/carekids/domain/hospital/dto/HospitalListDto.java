package com.aivle.carekids.domain.hospital.dto;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.hospital.model.HospitalType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class HospitalListDto {

    private Long hospitalId;

    private String hospitalName;

    private String hospitalType;

    private RegionDto hospitalRegion;

    @JsonProperty("operate-time")
    private List<OperateTimeDto> operateTimeDto;

    public HospitalListDto(Long hospitalId, String hospitalName, HospitalType hospitalType,
                           RegionDto hospitalRegion) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType.getHospitalType();
        this.hospitalRegion = hospitalRegion;
    }
}
