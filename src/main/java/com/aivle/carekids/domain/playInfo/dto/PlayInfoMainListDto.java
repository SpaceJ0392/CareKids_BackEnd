package com.aivle.carekids.domain.playInfo.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PlayInfoMainListDto {

    @JsonProperty("play-info")
    private List<PlayInfoListDto> playInfoListDtos;

    @JsonProperty("age-tag")
    private AgeTagDto ageTagDto;
}
