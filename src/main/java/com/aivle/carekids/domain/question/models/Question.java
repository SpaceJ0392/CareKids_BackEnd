package com.aivle.carekids.domain.question.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.question.dto.QuestionDetailDto;
import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Question extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(length = 100, nullable = false)
    private String questionTitle;

    @Lob
    private String questionText;


    private boolean questionCheck = false;

    private boolean deleted = false;

    private boolean secret = false;

    @Lob
    private String questionAnswer;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionFile> questionFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder
    private Question(String questionTitle, String questionText, boolean secret) {
        this.questionTitle = questionTitle;
        this.questionText = questionText;
        this.secret = secret;

    }

    public boolean getSecret(){ return secret; }
    public boolean getQuestionCheck(){ return questionCheck; }

    //입력에 대한 생성 메소드
    public void setUsersInfo(Users users){
        this.users = users;
        users.getQuestionUsers().add(this);
    }

    public void setDeletedInfo(boolean deleted){
        this.deleted = deleted;
        this.questionFiles.forEach(files -> {files.setDeletedInfo(deleted);});
    }

    public void setQuestionAnswerInfo(String questionAnswer){
        this.questionAnswer = questionAnswer;
        this.questionCheck = true;
    }

    public static Question createQuestion(QuestionDetailDto questionDetailDto){

        return Question.builder()
                .questionTitle(questionDetailDto.getQuestionTitle())
                .questionText(questionDetailDto.getQuestionText())
                .secret(questionDetailDto.getSecret())
                .build();
    }

    public void updateQuestion(QuestionDetailDto questionDetailDto){
        this.questionTitle = questionDetailDto.getQuestionTitle();
        this.questionText = questionDetailDto.getQuestionText();
        this.secret = questionDetailDto.getSecret();
    }
}
