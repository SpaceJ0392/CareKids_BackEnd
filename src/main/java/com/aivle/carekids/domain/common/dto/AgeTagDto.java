package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class AgeTagDto {

    @JsonProperty("id")
    private Long ageTagId;

    @JsonProperty("name")
    private String ageTagName;

    @JsonIgnore
    private Long kidsPolicyId;

    @Builder
    @QueryProjection
    public AgeTagDto(Long ageTagId, String ageTagName) {
        this.ageTagId = ageTagId;
        this.ageTagName = ageTagName;
    }

    public AgeTagDto(Long ageTagId, String ageTagName, Long kidsPolicyId) {
        this.ageTagId = ageTagId;
        this.ageTagName = ageTagName;
        this.kidsPolicyId = kidsPolicyId;
    }
}
