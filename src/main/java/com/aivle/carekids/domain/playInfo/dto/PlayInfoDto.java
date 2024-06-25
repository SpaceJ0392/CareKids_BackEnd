package com.aivle.carekids.domain.playInfo.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PlayInfoDto extends BaseDto {

    @NotEmpty @JsonProperty("id")
    private Long playInfoId;

    @NotEmpty @JsonProperty("title")
    private String playInfoTitle;

    @JsonProperty("description")
    private String playInfoText;

}
