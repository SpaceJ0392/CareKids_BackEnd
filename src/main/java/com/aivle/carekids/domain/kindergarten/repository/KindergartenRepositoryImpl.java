package com.aivle.carekids.domain.kindergarten.repository;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.dto.QRegionDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenListDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenOperateTimeDto;
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

import static com.aivle.carekids.domain.common.models.QRegion.region;
import static com.aivle.carekids.domain.hospital.model.QHospital.hospital;

import static com.aivle.carekids.domain.kindergarten.model.QKindergarten.kindergarten;
import static com.aivle.carekids.domain.kindergarten.model.QKindergartenOperateTime.kindergartenOperateTime;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class KindergartenRepositoryImpl implements KindergartenRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public RegionDto findRandomRegion() {
        return jpaQueryFactory.select(new QRegionDto(region.regionId, region.regionName)).from(hospital)
                .join(hospital.region, region)
                .orderBy(Expressions.numberTemplate(Integer.class, "function('RAND')").asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Page<KindergartenListDto> findAllByOrderByUpdatedAtDescByPageByRegion(Long regionId, Pageable pageable) {

        List<KindergartenListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                KindergartenListDto.class,
                                kindergarten.kindergartenId,
                                kindergarten.kindergartenName,
                                kindergarten.kindergartenAddress,
                                kindergarten.kindergartenNewaddress,
                                kindergarten.kindergartenPhone,
                                Projections.constructor(RegionDto.class, region.regionId, region.regionName)
                        )).from(kindergarten)
                .join(kindergarten.region, region)
                .where(regionEq(regionId))
                .orderBy(kindergarten.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<KindergartenOperateTimeDto>> operateTimeByKindergartenList = findOperateTimeByKindergartenList(content);
        content.forEach(c -> c.setKindergartenOperateTimeDto(operateTimeByKindergartenList.get(c.getKindergartenId())));

        JPAQuery<Long> countQuery = jpaQueryFactory.select(kindergarten.count()).from(kindergarten);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression regionEq(Long regionId) {
        return isEmpty(regionId) ? null : kindergarten.region.regionId.eq(regionId);
    }


    private Map<Long, List<KindergartenOperateTimeDto>> findOperateTimeByKindergartenList(List<KindergartenListDto> content) {
        List<Long> kindergartenIdList = content.stream().map(KindergartenListDto::getKindergartenId).toList();


        return jpaQueryFactory.select(
                        Projections.constructor(
                                KindergartenOperateTimeDto.class,
                                kindergartenOperateTime.dayType,
                                kindergartenOperateTime.startTime,
                                kindergartenOperateTime.endTime,
                                kindergarten.kindergartenId
                        ))
                .from(kindergartenOperateTime)
                .where(kindergartenOperateTime.kindergarten.kindergartenId.in(kindergartenIdList))
                .fetch().stream().collect(Collectors.groupingBy(KindergartenOperateTimeDto::getKindergartenId));

    }

    @Override
    public KindergartenDetailDto findKindergartenDetail(Long kindergartenId) {

        KindergartenDetailDto content = jpaQueryFactory.select(Projections.constructor(
                        KindergartenDetailDto.class,
                        kindergarten.kindergartenId,
                        kindergarten.kindergartenName,
                        kindergarten.kindergartenAddress,
                        kindergarten.kindergartenNewaddress,
                        kindergarten.kindergartenPhone,
                        Projections.constructor(RegionDto.class, region.regionId, region.regionName)
                )).from(kindergarten)
                .join(kindergarten.region, region)
                .where(kindergarten.kindergartenId.eq(kindergartenId)).fetchOne();

        Optional<KindergartenDetailDto> contentOptional = Optional.ofNullable(content);
        if (contentOptional.isEmpty()) {
            return null;
        }

        content.setKindergartenOperateTimes(
                jpaQueryFactory.select(
                                Projections.constructor(
                                        KindergartenOperateTimeDto.class,
                                        kindergartenOperateTime.dayType,
                                        kindergartenOperateTime.startTime,
                                        kindergartenOperateTime.endTime,
                                        kindergarten.kindergartenId
                                ))
                        .from(kindergartenOperateTime)
                        .where(kindergartenOperateTime.kindergarten.kindergartenId.eq(kindergartenId)).fetch()
        );

        return content;
    }

    @Override
    public Page<KindergartenListDto> searchKindergartenByFilter(SearchRegionDto searchRegionDto, Pageable pageable) {
        List<KindergartenListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                KindergartenListDto.class,
                                kindergarten.kindergartenId,
                                kindergarten.kindergartenName,
                                kindergarten.kindergartenAddress,
                                kindergarten.kindergartenNewaddress,
                                kindergarten.kindergartenPhone,
                                Projections.constructor(RegionDto.class, region.regionId, region.regionName)
                        )).from(kindergarten)
                .join(kindergarten.region, region)
                .where(
                        regionEq(searchRegionDto.getRegionDto().getRegionId()),
                        queryContains(searchRegionDto.getQuery())
                )
                .orderBy(kindergarten.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<KindergartenOperateTimeDto>> operateTimeByKindergartenList = findOperateTimeByKindergartenList(content);
        content.forEach(c -> c.setKindergartenOperateTimeDto(operateTimeByKindergartenList.get(c.getKindergartenId())));

        JPAQuery<Long> countQuery = jpaQueryFactory.select(kindergarten.count()).from(kindergarten);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression queryContains(String query) {
        return isEmpty(query) ? null : kindergarten.kindergartenName.containsIgnoreCase(query);
    }

}
