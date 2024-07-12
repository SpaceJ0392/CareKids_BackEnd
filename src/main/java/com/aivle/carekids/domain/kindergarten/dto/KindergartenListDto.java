package com.aivle.carekids.domain.kindergarten.dto;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.models.DayType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class KindergartenListDto {

    private Long kindergartenId;

    private String kindergartenName;

    private String kindergartenAddress;

    private String kindergartenNewaddress;

    private String kindergartenPhone;

    private RegionDto kindergartenRegion;

    @JsonProperty("operate-time")
    private List<KindergartenOperateTimeDto> kindergartenOperateTimeDto;

    public KindergartenListDto(Long kindergartenId, String kindergartenName, String kindergartenAddress, String kindergartenNewaddress, String kindergartenPhone, RegionDto kindergartenRegion) {
        this.kindergartenId = kindergartenId;
        this.kindergartenName = kindergartenName;
        this.kindergartenAddress = kindergartenAddress;
        this.kindergartenNewaddress = kindergartenNewaddress;
        this.kindergartenPhone = kindergartenPhone;
        this.kindergartenRegion = kindergartenRegion;
    }
}
