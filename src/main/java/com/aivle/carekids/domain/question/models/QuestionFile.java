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

    private String questionFilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
