package com.aivle.carekids.domain.hospital.dto;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.hospital.model.HospitalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class HospitalDetailDto {

    private Long hospitalId;

    private String hospitalName;

    private String hospitalAddress;

    private String hospitalNewaddress;

    private String hospitalPhone;

    private RegionDto region;

    private String hospitalType;

    private List<OperateTimeDto> hospitalOperateTimes = new ArrayList<>();

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
