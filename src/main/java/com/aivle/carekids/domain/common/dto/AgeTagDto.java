package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class AgeTagDto {

    @JsonProperty("id")
    private Long ageTagid;

    @JsonProperty("name")
    private String ageTagName;
}
