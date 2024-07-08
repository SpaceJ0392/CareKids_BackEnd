package com.aivle.carekids.domain.question.repository;

import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.question.models.QuestionFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.aivle.carekids.domain.question.models.QQuestion.question;
import static com.aivle.carekids.domain.question.models.QQuestionFile.questionFile;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Question> existsByQuestionTitleAndUserId(String questionTitle, Long usersId) {

        Question existsQuestion = jpaQueryFactory.selectFrom(question)
                .where(question.questionTitle.eq(questionTitle), question.users.usersId.eq(usersId))
                .fetchOne();

        return Optional.ofNullable(existsQuestion);
    }

    @Override
    public List<QuestionFile> findFilesByQuestionId(Long questionId) {

        return jpaQueryFactory.selectFrom(questionFile)
                .where(questionFile.question.questionId.eq(questionId))
                .fetch();
    }
}
