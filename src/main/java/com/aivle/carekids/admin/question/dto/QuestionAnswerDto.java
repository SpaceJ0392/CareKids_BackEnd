package com.aivle.carekids.admin.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class QuestionAnswerDto {

    @JsonProperty("id")
    private Long questionId;

    @JsonProperty("answer")
    private String questionAnswer;
}
