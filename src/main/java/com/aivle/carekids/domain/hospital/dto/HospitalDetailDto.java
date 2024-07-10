package com.aivle.carekids.domain.hospital.dto;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.hospital.model.HospitalType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class HospitalDetailDto {

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

    @JsonProperty("region")
    private RegionDto region;

    @JsonProperty("type")
    private String hospitalType;

    @JsonProperty("operate-time")
    private List<HospitalOperateTimeDto> hospitalOperateTimes = new ArrayList<>();

    public HospitalDetailDto(Long hospitalId, String hospitalName, String hospitalAddress, String hospitalNewaddress, String hospitalPhone, RegionDto region, HospitalType hospitalType) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalNewaddress = hospitalNewaddress;
        this.hospitalPhone = hospitalPhone;
        this.region = region;
        this.hospitalType = hospitalType.getHospitalType();
    }
}
