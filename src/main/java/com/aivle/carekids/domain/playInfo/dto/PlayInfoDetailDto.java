package com.aivle.carekids.domain.playInfo.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.models.AgeTag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayInfoDetailDto {

    @JsonProperty("id")
    private Long playInfoId;

    @JsonProperty("title")
    private String playInfoTitle;

    @JsonProperty("text")
    private String playInfoText;

    @JsonProperty("tools")
    private String playInfoTools;

    @JsonProperty("recommend-age")
    private String playInfoRecommendAge;

    @JsonProperty("age-tag")
    private AgeTagDto ageTag;

    @JsonProperty("dev-domains")
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
