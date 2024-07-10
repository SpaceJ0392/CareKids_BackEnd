package com.aivle.carekids.domain.common.dto;

import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyMainListDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoMainListDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeDto {

    @JsonProperty("kids-policy")
    private KidsPolicyMainListDto kidsPolicyMainListDto;

    @JsonProperty("play-info")
    private PlayInfoMainListDto playInfoMainListDto;
}
