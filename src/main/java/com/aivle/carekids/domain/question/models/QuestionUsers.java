package com.aivle.carekids.domain.question.models;

import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuestionUsers {
    @EmbeddedId
    private QuestionUsersId questionUsersId;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @MapsId("usersId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;
}
