package com.aivle.carekids.domain.question.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.aivle.carekids.domain.user.dto.UsersLightDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailDto extends BaseDto {

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

    public QuestionDetailDto(LocalDateTime createdAt, LocalDateTime updatedAt, Long questionId, String questionTitle, String questionText, boolean secret, UsersLightDto users, boolean questionCheck, String questionAnswer) {
        super(createdAt, updatedAt);
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionText = questionText;
        this.secret = secret;
        this.users = users;
        this.questionCheck = questionCheck;
        this.questionAnswer = questionAnswer;
    }
}
