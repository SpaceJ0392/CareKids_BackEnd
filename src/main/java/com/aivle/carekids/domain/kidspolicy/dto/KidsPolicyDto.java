package com.aivle.carekids.domain.kidspolicy.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class KidsPolicyDto extends BaseDto {

    @NotEmpty @JsonProperty("id")
    private Long kidsPolicyId;

    @NotEmpty @JsonProperty("title")
    private String kidsPolicyTitle;

    @JsonProperty("description")
    private String kidsPolicyText;
}
