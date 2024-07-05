package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.common.dto.RegionAgeDto;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyListDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class KidsPolicyRepositoryImpl implements KidsPolicyRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public RegionAgeDto findRandomRegionAndAgeTag() {
//        return jpaQueryFactory.select(new QRegionAgeDto(
//                new QRegionDto(kidsPolicyRegion.region.regionId, region.regionName),
//                new QAgeTagDto(kidsPolicyAgeTag.ageTag.ageTagId, ageTag.ageTagName)
//                )).from(kidsPolicy)
//                .join(kidsPolicy.kidsPolicyRegions, kidsPolicyRegion)
//                .join(kidsPolicyRegion.region, region)
//                .join(kidsPolicy.kidsPolicyAgeTags, kidsPolicyAgeTag)
//                .join(kidsPolicyAgeTag.ageTag, ageTag)
//                .orderBy(Expressions.numberTemplate(Integer.class, "function('RAND')").asc())
//                .limit(1)
//                .fetchOne();

        return null;
    }

    @Override
    public List<KidsPolicyListDto> findTop5ByRegionAndAgeTagOrderByUpdatedAtDesc(Long ageTagId, Long regionId) {
//        return jpaQueryFactory.select(new QKidsPolicyListDto(
//                kidsPolicy.createdAt, kidsPolicy.updatedAt, kidsPolicy.kidsPolicyId, kidsPolicy.kidsPolicyTitle,
//                                kidsPolicy.kidsPolicyText
//                )).from(kidsPolicy)
//                .join(kidsPolicy.kidsPolicyRegions, kidsPolicyRegion)
//                .join(kidsPolicy.kidsPolicyAgeTags, kidsPolicyAgeTag)
//                .where(
//                        kidsPolicyAgeTag.kidsPolicyAgeTagId.ageTagId.eq(ageTagId)
//                        .and(kidsPolicyRegion.kidsPolicyRegionId.regionId.eq(regionId))
//                )
//                .orderBy(kidsPolicy.updatedAt.desc())
//                .limit(4)
//                .fetch();

        return null;
    }
}
