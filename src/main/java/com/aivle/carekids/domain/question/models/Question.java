package com.aivle.carekids.domain.question.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    @Column(length = 100, nullable = false)
    private String questionTitle;

    @Lob
    private String questionText;

    private boolean deleted = false;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionFile> questionFiles = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionUsers> questionUsers = new ArrayList<>();

    // TODO - 입력에 대한 생성 메소드 필요
}
