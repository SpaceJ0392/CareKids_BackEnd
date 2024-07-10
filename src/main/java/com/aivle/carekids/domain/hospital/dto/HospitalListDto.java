package com.aivle.carekids.domain.hospital.dto;

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

    @JsonProperty("id")
    private Long hospitalId;

    @JsonProperty("name")
    private String hospitalName;

    @JsonProperty("type")
    private String hospitalType;

    @JsonProperty("region")
    private RegionDto hospitalRegion;

    @JsonProperty("operate-time")
    private List<HospitalOperateTimeDto> hospitalOperateTimeDto;

    public HospitalListDto(Long hospitalId, String hospitalName, HospitalType hospitalType,
                           RegionDto hospitalRegion) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType.getHospitalType();
        this.hospitalRegion = hospitalRegion;
    }
}
