package com.aivle.carekids.domain.question.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE question_file SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class QuestionFile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionFIleId;

    private String questionFileName;

    private String questionFileSaveName;

    private String questionFilePath;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public void setQuestionInfo(Question question)
    {
        this.question = question;
        question.getQuestionFiles().add(this);
    }

    public void setDeletedInfo(boolean deleted){
        this.deleted = deleted;
    }
}
