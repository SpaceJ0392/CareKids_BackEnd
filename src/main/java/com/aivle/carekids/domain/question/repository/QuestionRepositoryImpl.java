package com.aivle.carekids.domain.question.repository;

import com.aivle.carekids.domain.question.dto.QuestionDetailDisplayDto;
import com.aivle.carekids.domain.question.dto.QuestionDetailDto;
import com.aivle.carekids.domain.question.dto.QuestionFileDto;
import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.question.models.QuestionFile;
import com.aivle.carekids.domain.user.dto.UsersLightDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aivle.carekids.domain.question.models.QQuestion.question;
import static com.aivle.carekids.domain.question.models.QQuestionFile.questionFile;
import static com.aivle.carekids.domain.user.models.QUsers.users;

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


    @Override
    public Page<QuestionDetailDisplayDto> findAllOrderByQuestionIdDesc(Pageable pageable) {

        List<QuestionDetailDisplayDto> content = jpaQueryFactory.select(Projections.constructor(
                        QuestionDetailDisplayDto.class,
                        Projections.constructor(
                                QuestionDetailDto.class,
                                question.createdAt,
                                question.updatedAt,
                                question.questionId,
                                question.questionTitle,
                                question.questionText,
                                question.secret,
                                Projections.constructor(UsersLightDto.class, users.usersId, users.usersNickname),
                                question.questionCheck,
                                question.questionAnswer
                        )
                )).from(question)
                .join(question.users, users)
                .orderBy(question.questionId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> questionIdList = content.stream().map(q -> q.getQuestionDetailDto().getQuestionId()).toList();
        Map<Long, List<QuestionFileDto>> filesByQuestionIdList = findFilesByQuestionIdList(questionIdList);

        content.forEach(questionDetailDisplayDto -> {
            questionDetailDisplayDto
                    .setQuestionFileDtos(filesByQuestionIdList.get(questionDetailDisplayDto.getQuestionDetailDto().getQuestionId()));
        });

        JPAQuery<Long> countQuery = jpaQueryFactory.select(question.countDistinct()).from(question)
                .join(question.users, users);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    public Map<Long, List<QuestionFileDto>> findFilesByQuestionIdList(List<Long> questionIdList) {

        return jpaQueryFactory.select(Projections.constructor(
                        QuestionFileDto.class,
                        questionFile.question.questionId,
                        questionFile.questionFileName,
                        questionFile.questionFileSaveName,
                        questionFile.questionFilePath
                )).from(questionFile)
                .where(questionFile.question.questionId.in(questionIdList)).fetch()
                .stream().collect(Collectors.groupingBy(QuestionFileDto::getQuestionId));
    }
}
