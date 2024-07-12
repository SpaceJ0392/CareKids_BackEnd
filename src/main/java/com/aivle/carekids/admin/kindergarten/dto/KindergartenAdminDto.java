package com.aivle.carekids.admin.kindergarten.dto;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenOperateTimeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class KindergartenAdminDto {

    private Long kindergartenId;

    private String kindergartenName;

    private String kindergartenAddress;

    private String kindergartenNewaddress;

    private String kindergartenPhone;

    private RegionDto kindergartenRegion;

    @JsonProperty("operate-time")
    private List<KindergartenOperateTimeDto> kindergartenOperateTimeDto;

    public KindergartenAdminDto(Long kindergartenId, String kindergartenName, String kindergartenAddress, String kindergartenNewaddress, String kindergartenPhone, RegionDto kindergartenRegion) {
        this.kindergartenId = kindergartenId;
        this.kindergartenName = kindergartenName;
        this.kindergartenAddress = kindergartenAddress;
        this.kindergartenNewaddress = kindergartenNewaddress;
        this.kindergartenPhone = kindergartenPhone;
        this.kindergartenRegion = kindergartenRegion;
    }

}
