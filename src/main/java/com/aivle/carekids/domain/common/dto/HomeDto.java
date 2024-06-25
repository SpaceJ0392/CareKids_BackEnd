package com.aivle.carekids.domain.common.dto;

import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDto;
import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeDto {

    @JsonProperty("kids-policy")
    private List<KidsPolicyDto> kidsPolicyDto;

    @JsonProperty("notice")
    private List<NoticeDto> noticeDto;

    @JsonProperty("play-info")
    private List<PlayInfoDto> playInfoDto;
}
