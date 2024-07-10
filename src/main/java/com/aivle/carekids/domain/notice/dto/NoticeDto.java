package com.aivle.carekids.domain.notice.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class NoticeDto extends BaseDto {

    @NotEmpty @JsonProperty("id")
    private Long noticeId;

    @NotEmpty @JsonProperty("title")
    private String noticeTitle;

    @NotEmpty @JsonProperty("img")
    private String noticeImgUrl;

    @JsonProperty("description")
    @JsonRawValue
    private String noticeText;

    public NoticeDto(Long noticeId, String noticeTitle, String noticeText, String noticeImgUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.noticeId = noticeId;
        this.noticeText = noticeText;
        this.noticeImgUrl = noticeImgUrl;
        this.noticeTitle = noticeTitle;
    }
}
