package com.aivle.carekids.admin.question.service;

import com.aivle.carekids.admin.question.dto.QuestionAnswerDto;
import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionAdminService {

    private final QuestionRepository questionRepository;

    @Transactional
    public ResponseEntity<?> editAnswerForQuestion(QuestionAnswerDto questionAnswerDto) {
        Optional<Question> targetQuestion = questionRepository.findById(questionAnswerDto.getQuestionId());

        if (targetQuestion.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-find-error", "해당 문의사항을 찾을 수 없습니다."));
        }

        targetQuestion.get().setQuestionAnswerInfo(questionAnswerDto.getQuestionAnswer());
        return ResponseEntity.noContent().build();
    }
}
