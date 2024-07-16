package com.aivle.carekids.domain.kidspolicy.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.BaseDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class KidsPolicyDetailDto extends BaseDto {

    @JsonProperty("id")
    private Long kidsPolicyId;

    @JsonProperty("title")
    private String kidsPolicyTitle;

    @JsonProperty("text")
    private String kidsPolicyText;

    @JsonProperty("target")
    private String kidsPolicyTarget;

    @JsonProperty("process")
    private String kidsPolicyProcess;

    @JsonProperty("type")
    private String kidsPolicyType;

    @JsonProperty("url")
    private String kidsPolicyUrl;

    @JsonProperty("region")
    private List<RegionDto> regionDtos;

    @JsonProperty("age-tag")
    private List<AgeTagDto> ageTagDtos;

    public KidsPolicyDetailDto(LocalDateTime createdAt, LocalDateTime updatedAt, Long kidsPolicyId, String kidsPolicyTitle, String kidsPolicyText, String kidsPolicyTarget, String kidsPolicyProcess, KidsPolicyType kidsPolicyType, String kidsPolicyUrl) {
        super(createdAt, updatedAt);
        this.kidsPolicyId = kidsPolicyId;
        this.kidsPolicyTitle = kidsPolicyTitle;
        this.kidsPolicyText = kidsPolicyText;
        this.kidsPolicyTarget = kidsPolicyTarget;
        this.kidsPolicyProcess = kidsPolicyProcess;
        this.kidsPolicyType = kidsPolicyType.getKidsPolicyType();
        this.kidsPolicyUrl = kidsPolicyUrl;
    }

    public KidsPolicyDetailDto(Long kidsPolicyId, String kidsPolicyTitle, String kidsPolicyText, String kidsPolicyTarget, String kidsPolicyProcess, String kidsPolicyUrl) {
        this.kidsPolicyId = kidsPolicyId;
        this.kidsPolicyTitle = kidsPolicyTitle;
        this.kidsPolicyText = kidsPolicyText;
        this.kidsPolicyTarget = kidsPolicyTarget;
        this.kidsPolicyProcess = kidsPolicyProcess;
        this.kidsPolicyUrl = kidsPolicyUrl;
    }
}
