package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RegionDto {

    @JsonProperty("id")
    @NotNull(message = "지역을 선택해주세요")
    private Long regionId;

    @JsonProperty("name")
    @NotBlank(message = "지역을 선택해주세요")
    private String regionName;
}
