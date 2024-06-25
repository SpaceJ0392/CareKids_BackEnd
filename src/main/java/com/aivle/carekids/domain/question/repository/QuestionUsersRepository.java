package com.aivle.carekids.domain.question.repository;

import com.aivle.carekids.domain.question.models.QuestionUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionUsersRepository extends JpaRepository<QuestionUsers, Long> {
}
