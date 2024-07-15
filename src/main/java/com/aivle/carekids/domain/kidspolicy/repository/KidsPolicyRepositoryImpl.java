package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.common.dto.*;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.aivle.carekids.domain.kidspolicy.dto.QKidsPolicyListDto;
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
import static com.aivle.carekids.domain.common.models.QRegion.region;
import static com.aivle.carekids.domain.kidspolicy.models.QKidsPolicy.kidsPolicy;
import static com.aivle.carekids.domain.kidspolicy.models.QKidsPolicyAgeTag.kidsPolicyAgeTag;
import static com.aivle.carekids.domain.kidspolicy.models.QKidsPolicyRegion.kidsPolicyRegion;
import static com.aivle.carekids.domain.kidspolicy.models.QKidsPolicyRegionAgeTag.kidsPolicyRegionAgeTag;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class KidsPolicyRepositoryImpl implements KidsPolicyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public RegionAgeDto findRandomRegionAndAgeTag() {
        return jpaQueryFactory.select(new QRegionAgeDto(
                        new QRegionDto(region.regionId, region.regionName),
                        new QAgeTagDto(ageTag.ageTagId, ageTag.ageTagName)
                ))
                .from(kidsPolicyRegionAgeTag)
                .join(kidsPolicyRegionAgeTag.region, region)
                .join(kidsPolicyRegionAgeTag.ageTag, ageTag)
                .orderBy(Expressions.numberTemplate(Integer.class, "function('RAND')").asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<KidsPolicyListDto> findTop4ByRegionOrderByUpdatedAtDesc(Long regionId) {

        List<KidsPolicyListDto> content = jpaQueryFactory.select(new QKidsPolicyListDto(
                        kidsPolicy.createdAt, kidsPolicy.updatedAt, kidsPolicy.kidsPolicyId, kidsPolicy.kidsPolicyTitle,
                        kidsPolicy.kidsPolicyText
                )).from(kidsPolicyRegion)
                .join(kidsPolicyRegion.kidsPolicy, kidsPolicy)
                .where(kidsPolicyRegion.region.regionName.eq("전체").or(regionEq(regionId)))
                .orderBy(kidsPolicy.updatedAt.desc())
                .limit(4)
                .fetch();

        Map<Long, List<RegionDto>> regionsByKidsPolicyList = findRegionsByKidsPolicyList(content);
        content.forEach(c -> c.setRegionDtos(regionsByKidsPolicyList.get(c.getKidsPolicyId())));

        Map<Long, List<AgeTagDto>> ageTagByKidsPolicyList = findAgeTagByKidsPolicyList(content);
        content.forEach(c -> c.setAgeTagDtos(ageTagByKidsPolicyList.get(c.getKidsPolicyId())));

        return content;
    }

    @Override
    public Page<KidsPolicyListDto> findByRegionAndAgeTagOrderByUpdatedAtDescPage(Long regionId, Pageable pageable) {

        List<KidsPolicyListDto> content = jpaQueryFactory.select(new QKidsPolicyListDto(
                        kidsPolicy.createdAt, kidsPolicy.updatedAt, kidsPolicy.kidsPolicyId, kidsPolicy.kidsPolicyTitle,
                        kidsPolicy.kidsPolicyText
                )).from(kidsPolicyRegion)
                .join(kidsPolicyRegion.kidsPolicy, kidsPolicy)
                .where(regionIn(regionId))
                .orderBy(
                        kidsPolicy.updatedAt.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        Map<Long, List<RegionDto>> regionsByKidsPolicyList = findRegionsByKidsPolicyList(content);
        content.forEach(c -> c.setRegionDtos(regionsByKidsPolicyList.get(c.getKidsPolicyId())));

        Map<Long, List<AgeTagDto>> ageTagByKidsPolicyList = findAgeTagByKidsPolicyList(content);
        content.forEach(c -> c.setAgeTagDtos(ageTagByKidsPolicyList.get(c.getKidsPolicyId())));

        JPAQuery<Long> countQuery = jpaQueryFactory.select(kidsPolicy.count()).from(kidsPolicyRegion)
                .join(kidsPolicyRegion.kidsPolicy, kidsPolicy)
                .where(regionIn(regionId))
                .distinct();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public KidsPolicyDetailDto findKidsPolicyDetail(Long kidsPolicyId) {

        KidsPolicyDetailDto content = jpaQueryFactory.select(Projections.constructor(
                        KidsPolicyDetailDto.class,
                        kidsPolicy.kidsPolicyId,
                        kidsPolicy.kidsPolicyTitle,
                        kidsPolicy.kidsPolicyText,
                        kidsPolicy.kidsPolicyTarget,
                        kidsPolicy.kidsPolicyProcess,
                        kidsPolicy.kidsPolicyUrl
                ))
                .from(kidsPolicy)
                .where(kidsPolicy.kidsPolicyId.eq(kidsPolicyId))
                .fetchOne();

        Optional<KidsPolicyDetailDto> contentOptional = Optional.ofNullable(content);
        if (contentOptional.isEmpty()){ return  null; }

        content.setRegionDtos(
                jpaQueryFactory.select(Projections.constructor(
                                RegionDto.class,
                                region.regionId,
                                region.regionName
                        )).from(kidsPolicyRegion)
                        .join(kidsPolicyRegion.region, region)
                        .where(kidsPolicyRegion.kidsPolicy.kidsPolicyId.eq(content.getKidsPolicyId()))
                        .fetch()
        );

        content.setAgeTagDtos(
                jpaQueryFactory.select(Projections.constructor(
                                AgeTagDto.class,
                                ageTag.ageTagId,
                                ageTag.ageTagName
                        )).from(kidsPolicyAgeTag)
                        .join(kidsPolicyAgeTag.ageTag, ageTag)
                        .where(kidsPolicyAgeTag.kidsPolicy.kidsPolicyId.eq(content.getKidsPolicyId()))
                        .fetch()
        );

        return content;
    }

    @Override
    public Page<KidsPolicyListDto> searchKidsPolicyByFilter(SearchRegionAgeTagDto searchRegionAgeTagDto, Pageable pageable) {

        List<Long> ageTagIdList = null;
        if(searchRegionAgeTagDto.getAgeTagDto().getAgeTagId() != null){
            ageTagIdList = List.of(searchRegionAgeTagDto.getAgeTagDto().getAgeTagId());
        }

        List<KidsPolicyListDto> content = jpaQueryFactory.
                select(new QKidsPolicyListDto(
                        kidsPolicy.createdAt, kidsPolicy.updatedAt, kidsPolicy.kidsPolicyId, kidsPolicy.kidsPolicyTitle,
                        kidsPolicy.kidsPolicyText
                ))
                .from(kidsPolicy)
                .join(kidsPolicy.kidsPolicyRegions, kidsPolicyRegion)
                .join(kidsPolicyRegion.region, region)
                .join(kidsPolicy.kidsPolicyAgeTags, kidsPolicyAgeTag)
                .join(kidsPolicyAgeTag.ageTag, ageTag)
                .where(
                        queryContains(searchRegionAgeTagDto.getQuery()),
                        regionSearchIn(searchRegionAgeTagDto.getRegionDto().getRegionId()),
                        ageTagSearchIn(ageTagIdList)
                )
                .distinct()
                .orderBy(kidsPolicy.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<RegionDto>> regionsByKidsPolicyList = findRegionsByKidsPolicyList(content);
        content.forEach(c -> c.setRegionDtos(regionsByKidsPolicyList.get(c.getKidsPolicyId())));

        Map<Long, List<AgeTagDto>> ageTagByKidsPolicyList = findAgeTagByKidsPolicyList(content);
        content.forEach(c -> c.setAgeTagDtos(ageTagByKidsPolicyList.get(c.getKidsPolicyId())));

        JPAQuery<Long> countQuery = jpaQueryFactory.select(kidsPolicy.count()).from(kidsPolicyRegionAgeTag)
                .join(kidsPolicyRegionAgeTag.kidsPolicy, kidsPolicy)
                .join(kidsPolicyRegionAgeTag.region, region)
                .join(kidsPolicyRegionAgeTag.ageTag, ageTag)
                .where(
                        queryContains(searchRegionAgeTagDto.getQuery()),
                        regionSearchIn(searchRegionAgeTagDto.getRegionDto().getRegionId()),
                        ageTagSearchIn(ageTagIdList)
                )
                .distinct();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression regionSearchIn(Long regionId) {
        return isEmpty(regionId) ? null : kidsPolicyRegion.region.regionId.eq(regionId);
    }

    private BooleanExpression ageTagSearchIn(List<Long> ageTagIds) {
        return isEmpty(ageTagIds) ? null : kidsPolicyAgeTag.ageTag.ageTagId.in(ageTagIds);
    }

    private BooleanExpression regionEq(Long regionId) {
        return isEmpty(regionId) ? null : kidsPolicyRegion.region.regionId.eq(regionId);
    }

    private BooleanExpression regionIn(Long regionId) {
        return isEmpty(regionId) || regionId == 26 ? null : kidsPolicyRegion.region.regionId.eq(regionId);
    }

    private BooleanExpression ageTagIn(List<Long> ageTagIds) {
        return ageTagIds == null || ageTagIds.isEmpty() ? null : kidsPolicyAgeTag.ageTag.ageTagId.in(ageTagIds);
    }

    private BooleanExpression queryContains(String query) {
        return isEmpty(query) ? null : kidsPolicy.kidsPolicyTitle.containsIgnoreCase(query);
    }

    private Map<Long, List<RegionDto>> findRegionsByKidsPolicyList(List<KidsPolicyListDto> content) {

        List<Long> kidsPolicyIdList = content.stream().map(KidsPolicyListDto::getKidsPolicyId).toList();

        return jpaQueryFactory.select(
                        Projections.constructor(
                                RegionDto.class,
                                region.regionId,
                                region.regionName,
                                kidsPolicyRegion.kidsPolicy.kidsPolicyId
                        ))
                .from(kidsPolicyRegion)
                .join(kidsPolicyRegion.region, region)
                .where(
                        kidsPolicyRegion.kidsPolicy.kidsPolicyId.in(kidsPolicyIdList)
                )
                .fetch().stream().collect(Collectors.groupingBy(RegionDto::getKidsPolicyId));
    }

    private Map<Long, List<AgeTagDto>> findAgeTagByKidsPolicyList(List<KidsPolicyListDto> content) {

        List<Long> kidsPolicyIdList = content.stream().map(KidsPolicyListDto::getKidsPolicyId).toList();

        return jpaQueryFactory.select(
                        Projections.constructor(
                                AgeTagDto.class,
                                ageTag.ageTagId,
                                ageTag.ageTagName,
                                kidsPolicyAgeTag.kidsPolicy.kidsPolicyId
                        ))
                .from(kidsPolicyAgeTag)
                .join(kidsPolicyAgeTag.ageTag, ageTag)
                .where(
                        kidsPolicyAgeTag.kidsPolicy.kidsPolicyId.in(kidsPolicyIdList)
                )
                .fetch().stream().collect(Collectors.groupingBy(AgeTagDto::getKidsPolicyId));
    }
}