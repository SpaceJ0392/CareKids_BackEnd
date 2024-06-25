package com.aivle.carekids.domain.question.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class QuestionUsersId implements Serializable {
    private Long questionId;
    private Long usersId;
}
