package com.aivle.carekids.domain.kidspolicy.dto;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class KidsPolicyMainListDto{

    @JsonProperty("region")
    private List<RegionDto> regionDto;

    @JsonProperty("kids-policy")
    private List<KidsPolicyListDto> kidsPolicyListDtos;
}
