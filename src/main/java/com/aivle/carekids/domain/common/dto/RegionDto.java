package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class RegionDto {

    @JsonProperty("id")
    @NotNull(message = "지역을 선택해주세요")
    private Long regionId;

    @JsonProperty("name")
    @NotBlank(message = "지역을 선택해주세요")
    private String regionName;

    @JsonIgnore
    private Long kidsPolicyId;

    @Builder
    @QueryProjection
    public RegionDto(Long regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public RegionDto(Long regionId, String regionName, Long kidsPolicyId) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.kidsPolicyId = kidsPolicyId;
    }
}
