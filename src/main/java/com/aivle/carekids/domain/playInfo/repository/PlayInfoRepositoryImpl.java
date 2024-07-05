package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.QAgeTagDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoListDto;
import com.aivle.carekids.domain.playInfo.dto.QPlayInfoListDto;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.aivle.carekids.domain.common.models.QAgeTag.ageTag;
import static com.aivle.carekids.domain.playInfo.models.QPlayInfo.playInfo;

@RequiredArgsConstructor
public class PlayInfoRepositoryImpl implements PlayInfoRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AgeTagDto findRandomAgeTagInPlayInfo() {
        return jpaQueryFactory.select(new QAgeTagDto(ageTag.ageTagId, ageTag.ageTagName)).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .orderBy(Expressions.numberTemplate(Integer.class, "function('RAND')").asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<PlayInfoListDto> findTop4ByAgeTagOrderByUpdatedAtDesc(Long ageTagId) {

        return jpaQueryFactory
                .select(new QPlayInfoListDto(
                        playInfo.createdAt,
                        playInfo.updatedAt,
                        playInfo.playInfoId,
                        playInfo.playInfoTitle,
                        playInfo.playInfoText))
                .from(playInfo)
                .where(playInfo.ageTag.ageTagId.eq(ageTagId))
                .orderBy(playInfo.updatedAt.desc())
                .limit(4)
                .fetch();
    }
}
