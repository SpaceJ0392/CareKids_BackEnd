package com.aivle.carekids.domain.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailDisplayDto {

    @JsonProperty("data")
    private QuestionDetailDto questionDetailDto;

    @JsonProperty("files")
    private List<QuestionFileDto> questionFileDtos;

    public QuestionDetailDisplayDto(QuestionDetailDto questionDetailDto) {
        this.questionDetailDto = questionDetailDto;
    }
}
