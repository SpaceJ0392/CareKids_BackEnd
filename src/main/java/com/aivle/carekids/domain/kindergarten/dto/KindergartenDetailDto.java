package com.aivle.carekids.domain.kindergarten.dto;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class KindergartenDetailDto {

    private Long kindergartenId;

    private String kindergartenName;

    private String kindergartenAddress;

    private String kindergartenNewaddress;

    private String kindergartenPhone;

    private RegionDto region;

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
