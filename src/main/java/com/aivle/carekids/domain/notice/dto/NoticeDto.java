package com.aivle.carekids.domain.notice.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class NoticeDto extends BaseDto {

    @NotEmpty @JsonProperty("id")
    private Long noticeId;

    @NotEmpty @JsonProperty("title")
    private String noticeTitle;

    @JsonProperty("description")
    private String noticeText;
}
