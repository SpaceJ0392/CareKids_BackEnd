package com.aivle.carekids.domain.playInfo.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
public class PlayInfoListDto extends BaseDto {

    @NotEmpty @JsonProperty("id")
    private Long playInfoId;

    @NotEmpty @JsonProperty("title")
    private String playInfoTitle;

    @JsonProperty("description")
    private String playInfoText;

    @QueryProjection
    public PlayInfoListDto(LocalDateTime createdAt, LocalDateTime updatedAt, Long playInfoId, String playInfoTitle, String playInfoText) {
        super(createdAt, updatedAt);
        this.playInfoId = playInfoId;
        this.playInfoTitle = playInfoTitle;
        this.playInfoText = playInfoText;
    }
}
