package com.aivle.carekids.domain.question.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.aivle.carekids.domain.user.dto.UsersLightDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionListDto extends BaseDto {
    @JsonProperty("id")
    private Long questionId;

    @JsonProperty("title")
    private String questionTitle;

    private boolean secret;

    private boolean questionCheck;

    private UsersLightDto users;

    public QuestionListDto(LocalDateTime createdAt, LocalDateTime updatedAt, Long questionId, String questionTitle, boolean secret, boolean questionCheck, UsersLightDto users) {
        super(createdAt, updatedAt);
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.secret = secret;
        this.questionCheck = questionCheck;
        this.users = users;
    }
}
