package com.aivle.carekids.domain.kindergarten.dto;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class KindergartenDetailDto {

    @JsonProperty("id")
    private Long kindergartenId;

    @JsonProperty("name")
    private String kindergartenName;

    @JsonProperty("address")
    private String kindergartenAddress;

    @JsonProperty("new-address")
    private String kindergartenNewaddress;

    @JsonProperty("phone")
    private String kindergartenPhone;

    @JsonProperty("region")
    private RegionDto region;

    @JsonProperty("operate-time")
    private List<KindergartenOperateTimeDto> kindergartenOperateTimes = new ArrayList<>();

    public KindergartenDetailDto(Long kindergartenId, String kindergartenName, String kindergartenAddress, String kindergartenNewaddress, String kindergartenPhone, RegionDto region) {
        this.kindergartenId = kindergartenId;
        this.kindergartenName = kindergartenName;
        this.kindergartenAddress = kindergartenAddress;
        this.kindergartenNewaddress = kindergartenNewaddress;
        this.kindergartenPhone = kindergartenPhone;
        this.region = region;
    }
}
