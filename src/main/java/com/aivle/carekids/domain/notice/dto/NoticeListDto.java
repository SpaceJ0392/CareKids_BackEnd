package com.aivle.carekids.domain.notice.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoticeListDto extends BaseDto {

    @NotEmpty
    @JsonProperty("id")
    private Long noticeId;

    @NotEmpty @JsonProperty("title")
    private String noticeTitle;

    public NoticeListDto(Long noticeId, String noticeTitle, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
    }
}
