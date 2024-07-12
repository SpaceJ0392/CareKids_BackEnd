package com.aivle.carekids.admin.utils.dto;

import com.aivle.carekids.domain.common.models.DayType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperateDateTypeDto {

    @JsonProperty("id")
    public DayType dayType;

    @JsonProperty("operate-day")
    public String dayTypeName;
}
