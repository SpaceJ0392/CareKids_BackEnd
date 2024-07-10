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

    @JsonProperty("address")
    private String hospitalAddress;

    @JsonProperty("new-address")
    private String hospitalNewaddress;

    @JsonProperty("phone")
    private String hospitalPhone;

    @JsonProperty("type")
    private String hospitalType;

    @JsonProperty("region")
    private RegionDto hospitalRegion;

    @JsonProperty("operate-time")
    private List<HospitalOperateTimeDto> hospitalOperateTimeDto;

    public HospitalListDto(Long hospitalId, String hospitalName, String hospitalAddress, String hospitalNewaddress, String hospitalPhone, HospitalType hospitalType, RegionDto hospitalRegion) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalNewaddress = hospitalNewaddress;
        this.hospitalPhone = hospitalPhone;
        this.hospitalType = hospitalType.getHospitalType();
        this.hospitalRegion = hospitalRegion;
    }
}