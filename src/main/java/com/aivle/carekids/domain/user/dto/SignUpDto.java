package com.aivle.carekids.domain.user.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@AllArgsConstructor
public class SignUpDto {
    @JsonProperty("age-tag")
    private List<AgeTagDto> ageTag;
    private List<RegionDto> region;
}
