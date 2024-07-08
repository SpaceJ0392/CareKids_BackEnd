package com.aivle.carekids.domain.question.repository;

import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.question.models.QuestionFile;

import java.util.List;
import java.util.Optional;

public interface QuestionRepositoryCustom {
    Optional<Question> existsByQuestionTitleAndUserId(String questionTitle, Long usersId);

    List<QuestionFile> findFilesByQuestionId(Long questionId);
}
