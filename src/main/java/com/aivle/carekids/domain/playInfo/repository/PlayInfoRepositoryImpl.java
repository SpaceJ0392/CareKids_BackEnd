package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.QAgeTagDto;
import com.aivle.carekids.domain.common.dto.SearchAgeTagDto;
import com.aivle.carekids.domain.playInfo.dto.DevDomainDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDetailDto;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoListDto;
import com.aivle.carekids.domain.playInfo.dto.QPlayInfoListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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

import static com.aivle.carekids.domain.common.models.QAgeTag.ageTag;
import static com.aivle.carekids.domain.playInfo.model.QDevDomain.devDomain;
import static com.aivle.carekids.domain.playInfo.model.QPlayInfo.playInfo;
import static com.aivle.carekids.domain.playInfo.model.QPlayInfoDomain.playInfoDomain;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class PlayInfoRepositoryImpl implements PlayInfoRepositoryCustom {

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
                .where(playInfo.ageTag.ageTagName.eq("전체(12세 이하)").or(ageEq(ageTagId)))
                .orderBy(playInfo.updatedAt.desc())
                .limit(4)
                .fetch();
    }


    @Override
    public Page<PlayInfoListDto> findAllByOrderByUpdatedAtDescByPageByAge(Long ageTagId, Pageable pageable) {

        List<PlayInfoListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                PlayInfoListDto.class,
                                playInfo.createdAt,
                                playInfo.updatedAt,
                                playInfo.playInfoId,
                                playInfo.playInfoTitle,
                                playInfo.playInfoText
                        )).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .where(ageEq(ageTagId))
                .orderBy(playInfo.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(playInfo.count()).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .where(ageEq(ageTagId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<PlayInfoDetailDto> findAllByOrderByUpdatedAtDescByPageByAgeAdmin(Long ageTagId, Pageable pageable) {

        List<PlayInfoDetailDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                PlayInfoDetailDto.class,
                                playInfo.playInfoId,
                                playInfo.playInfoTitle,
                                playInfo.playInfoText,
                                playInfo.playInfoTools,
                                playInfo.playInfoRecommendAge,
                                Projections.constructor(AgeTagDto.class, ageTag.ageTagId, ageTag.ageTagName)
                        )).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .where(ageEq(ageTagId))
                .orderBy(playInfo.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> playInfoId = content.stream().map(PlayInfoDetailDto::getPlayInfoId).toList();
        Map<Long, List<DevDomainDto>> contentDevDomainById = findContentDevDomainById(playInfoId);

        content.forEach(playInfo -> {
            playInfo.setDevDomains(contentDevDomainById.get(playInfo.getPlayInfoId()));
        });

        JPAQuery<Long> countQuery = jpaQueryFactory.select(playInfo.count()).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .where(ageEq(ageTagId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression ageEq(Long ageTagId) {
        return isEmpty(ageTagId) || ageTagId == 5 ? null : playInfo.ageTag.ageTagId.eq(ageTagId);
    }


    @Override
    public PlayInfoDetailDto findPlayInfoDetail(Long playInfoId) {

        PlayInfoDetailDto content = jpaQueryFactory.select(Projections.constructor(
                        PlayInfoDetailDto.class,
                        playInfo.playInfoId,
                        playInfo.playInfoTitle,
                        playInfo.playInfoText,
                        playInfo.playInfoTools,
                        playInfo.playInfoRecommendAge,
                        Projections.constructor(AgeTagDto.class, ageTag.ageTagId, ageTag.ageTagName)
                )).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .where(playInfo.playInfoId.eq(playInfoId)).fetchOne();

        Optional<PlayInfoDetailDto> contentOptional = Optional.ofNullable(content);
        if (contentOptional.isEmpty()) {
            return null;
        }

        content.setDevDomains(
                jpaQueryFactory.select(
                                Projections.constructor(
                                        DevDomainDto.class,
                                        devDomain.devDomainId,
                                        devDomain.devDomainType
                                ))
                        .from(playInfoDomain)
                        .join(playInfoDomain.devDomain, devDomain)
                        .where(playInfoDomain.playInfo.playInfoId.eq(playInfoId)).fetch()
        );

        return content;
    }

    @Override
    public Page<PlayInfoListDto> searchPlayInfoByFilter(SearchAgeTagDto searchAgeTagDto, Pageable pageable) {
        List<PlayInfoListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                PlayInfoListDto.class,
                                playInfo.createdAt,
                                playInfo.updatedAt,
                                playInfo.playInfoId,
                                playInfo.playInfoTitle,
                                playInfo.playInfoText
                        )).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .where(
                        ageEq(searchAgeTagDto.getAgeTagDto().getAgeTagId()),
                        queryContains(searchAgeTagDto.getQuery())
                )
                .orderBy(playInfo.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Long> countQuery = jpaQueryFactory.select(playInfo.count()).from(playInfo)
                .join(playInfo.ageTag, ageTag)
                .where(
                        ageEq(searchAgeTagDto.getAgeTagDto().getAgeTagId()),
                        queryContains(searchAgeTagDto.getQuery())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression queryContains(String query) {
        return isEmpty(query) ? null : playInfo.playInfoTitle.containsIgnoreCase(query);
    }

    private Map<Long, List<DevDomainDto>> findContentDevDomainById(List<Long> playInfoId) {

        return jpaQueryFactory.select(
                        Projections.constructor(
                                DevDomainDto.class,
                                devDomain.devDomainId,
                                devDomain.devDomainType,
                                playInfoDomain.playInfo.playInfoId
                        ))
                .from(playInfoDomain)
                .join(playInfoDomain.devDomain, devDomain)
                .where(playInfoDomain.playInfo.playInfoId.in(playInfoId))
                .fetch().stream().collect(Collectors.groupingBy(DevDomainDto::getPlayInfoId));
    }
}
