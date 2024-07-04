package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class AgeTagDto {

    @JsonProperty("id")
    private Long ageTagid;

    @JsonProperty("name")
    private String ageTagName;

    @QueryProjection
    public AgeTagDto(Long ageTagid, String ageTagName) {
        this.ageTagid = ageTagid;
        this.ageTagName = ageTagName;
    }
}
