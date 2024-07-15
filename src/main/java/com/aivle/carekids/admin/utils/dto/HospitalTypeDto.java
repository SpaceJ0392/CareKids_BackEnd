package com.aivle.carekids.admin.utils.dto;

import com.aivle.carekids.domain.hospital.model.HospitalType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalTypeDto {

    @JsonProperty("id")
    private HospitalType hospitalType;

    @JsonProperty("type")
    private String hospitalTypeName;
}
