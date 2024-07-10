package com.aivle.carekids.domain.playInfo.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.models.AgeTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayInfoDetailDto {

    private Long playInfoId;

    private String playInfoTitle;

    private String playInfoText;

    private String playInfoTools;

    private String playInfoRecommendAge;

    private AgeTagDto ageTag;

    private List<DevDomainDto> devDomains = new ArrayList<>();

    public PlayInfoDetailDto(Long playInfoId, String playInfoTitle, String playInfoText, String playInfoTools, String playInfoRecommendAge, AgeTagDto ageTag) {
        this.playInfoId = playInfoId;
        this.playInfoTitle = playInfoTitle;
        this.playInfoText = playInfoText;
        this.playInfoTools = playInfoTools;
        this.playInfoRecommendAge = playInfoRecommendAge;
        this.ageTag = ageTag;
    }
}
