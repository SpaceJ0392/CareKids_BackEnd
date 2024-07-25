package com.aivle.carekids.domain.playInfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DevDomainDto {

    private Long devDomainId;

    private String devDomainType;

    @JsonIgnore
    private Long playInfoId;
}
