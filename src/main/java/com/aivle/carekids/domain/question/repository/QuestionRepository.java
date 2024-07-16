package com.aivle.carekids.domain.question.repository;

import com.aivle.carekids.domain.question.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    Page<Question> findAllByOrderByQuestionIdDesc(Pageable pageable);
}
