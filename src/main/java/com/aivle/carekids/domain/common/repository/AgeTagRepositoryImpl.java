package com.aivle.carekids.domain.common.repository;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.aivle.carekids.domain.common.models.QAgeTag.ageTag;

@RequiredArgsConstructor
public class AgeTagRepositoryImpl implements AgeTagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AgeTag findRandomAgeTag() {
        return jpaQueryFactory.selectFrom(ageTag)
                .orderBy(Expressions.numberTemplate(Integer.class, "function('RAND')").asc())
                .limit(1)
                .fetchOne();
    }
}
