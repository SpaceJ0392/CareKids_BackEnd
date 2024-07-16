package com.aivle.carekids.domain.question.dto;

import com.aivle.carekids.domain.user.dto.UsersLightDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailDto {

    @JsonProperty("id")
    private Long questionId;

    @JsonProperty("title")
    private String questionTitle;

    @JsonProperty("text")
    private String questionText;

    @JsonProperty("secret")
    private boolean secret;

    @JsonProperty("author")
    private UsersLightDto users;

    @JsonProperty("check")
    private boolean questionCheck;

    @JsonProperty("answer")
    private String questionAnswer;

    public boolean getSecret(){
        return secret;
    }
}
