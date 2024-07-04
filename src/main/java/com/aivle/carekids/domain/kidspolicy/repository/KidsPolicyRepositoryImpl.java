package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.common.dto.QAgeTagDto;
import com.aivle.carekids.domain.common.dto.QRegionDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyRegionAgeDto;
import com.aivle.carekids.domain.kidspolicy.dto.QKidsPolicyListDto;
import com.aivle.carekids.domain.kidspolicy.dto.QKidsPolicyRegionAgeDto;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.aivle.carekids.domain.common.models.QAgeTag.ageTag;
import static com.aivle.carekids.domain.common.models.QRegion.region;
import static com.aivle.carekids.domain.kidspolicy.models.QKidsPolicy.kidsPolicy;
import static com.aivle.carekids.domain.kidspolicy.models.QKidsPolicyAgeTag.kidsPolicyAgeTag;
import static com.aivle.carekids.domain.kidspolicy.models.QKidsPolicyRegion.kidsPolicyRegion;

@RequiredArgsConstructor
public class KidsPolicyRepositoryImpl implements KidsPolicyRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public KidsPolicyRegionAgeDto findRandomRegionAndAgeTag() {
        return jpaQueryFactory.select(new QKidsPolicyRegionAgeDto(
                new QKidsPolicyListDto(kidsPolicy.createdAt, kidsPolicy.updatedAt, kidsPolicy.kidsPolicyId, kidsPolicy.kidsPolicyTitle,
                                kidsPolicy.kidsPolicyText),
                new QRegionDto(region.regionId, region.regionName),
                new QAgeTagDto(ageTag.ageTagId, ageTag.ageTagName)
                )).from(kidsPolicy)
                .join(kidsPolicy.kidsPolicyRegions, kidsPolicyRegion)
                .join(kidsPolicyRegion.region, region)
                .join(kidsPolicy.kidsPolicyAgeTags, kidsPolicyAgeTag)
                .join(kidsPolicyAgeTag.ageTag, ageTag)
                .orderBy(Expressions.numberTemplate(Integer.class, "function('RAND')").asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<KidsPolicyListDto> findTop5ByRegionAndAgeTagOrderByUpdatedAtDesc(Long ageTagId, Long regionId) {
        return jpaQueryFactory.select(new QKidsPolicyListDto(
                kidsPolicy.createdAt, kidsPolicy.updatedAt, kidsPolicy.kidsPolicyId, kidsPolicy.kidsPolicyTitle,
                                kidsPolicy.kidsPolicyText
                )).from(kidsPolicy)
                .join(kidsPolicy.kidsPolicyRegions, kidsPolicyRegion)
                .join(kidsPolicy.kidsPolicyAgeTags, kidsPolicyAgeTag)
                .where(
                        kidsPolicyAgeTag.kidsPolicyAgeTagId.ageTagId.eq(ageTagId)
                        .and(kidsPolicyRegion.kidsPolicyRegionId.regionId.eq(regionId))
                )
                .orderBy(kidsPolicy.updatedAt.desc())
                .limit(4)
                .fetch();
    }
}
