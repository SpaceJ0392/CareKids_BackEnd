package com.aivle.carekids.domain.kidspolicy.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.BaseDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
public class KidsPolicyListDto extends BaseDto {

    @NotEmpty @JsonProperty("id")
    private Long kidsPolicyId;

    @NotEmpty @JsonProperty("title")
    private String kidsPolicyTitle;

    @JsonProperty("description")
    @JsonRawValue
    private String kidsPolicyText;

    @JsonProperty("region")
    private List<RegionDto> regionDtos;

    @JsonProperty("age-tag")
    private List<AgeTagDto> ageTagDtos;

    @QueryProjection
    public KidsPolicyListDto(LocalDateTime createdAt, LocalDateTime updatedAt, Long kidsPolicyId, String kidsPolicyTitle, String kidsPolicyText) {
        super(createdAt, updatedAt);
        this.kidsPolicyId = kidsPolicyId;
        this.kidsPolicyTitle = kidsPolicyTitle;
        this.kidsPolicyText = kidsPolicyText;
    }
}
