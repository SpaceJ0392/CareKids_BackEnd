package com.aivle.carekids.domain.common.repository;

import com.aivle.carekids.domain.common.models.Region;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.aivle.carekids.domain.common.models.QRegion.region;

@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Region findRandomRegion() {
        return jpaQueryFactory.selectFrom(region)
                .orderBy(Expressions.numberTemplate(Integer.class, "function('RAND')").asc())
                .limit(1)
                .fetchOne();
    }
}
