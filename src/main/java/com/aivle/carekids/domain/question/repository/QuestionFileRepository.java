package com.aivle.carekids.domain.question.repository;

import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.question.models.QuestionFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionFileRepository extends JpaRepository<QuestionFile, Long> {
    List<QuestionFile> findByQuestion(Question question);
}
