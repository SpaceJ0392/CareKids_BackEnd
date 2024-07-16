package com.aivle.carekids.domain.question.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class QuestionFileDto {

    @JsonIgnore
    private Long questionId;

    private String fileName;
    private String fileSaveName;

    @JsonProperty("file-path")
    private String questionFilePath;

    public QuestionFileDto(String fileName, String fileSaveName, String questionFilePath) {
        this.fileName = fileName;
        this.fileSaveName = fileSaveName;
        this.questionFilePath = questionFilePath;
    }

    public QuestionFileDto(Long questionId, String fileName, String fileSaveName, String questionFilePath) {
        this.questionId = questionId;
        this.fileName = fileName;
        this.fileSaveName = fileSaveName;
        this.questionFilePath = questionFilePath;
    }
}
