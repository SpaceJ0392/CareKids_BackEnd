package com.aivle.carekids.domain.hospital.repository;

import com.aivle.carekids.domain.common.dto.QRegionDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

import static com.aivle.carekids.domain.common.models.QRegion.region;
import static com.aivle.carekids.domain.hospital.model.QHospital.hospital;

@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepositoryCustom{

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
    public Page<HospitalListDto> findHospitalByFilter(Long regionId, Pageable pageable) {
//        return jpaQueryFactory.select(
//                Projections.constructor(
//                        HospitalListDto.class,
//                        hospital.hospitalId,
//                        hospital.hospitalName,
//                        hospital.hospitalType,
//                        Projections.constructor(RegionDto.class, region.regionId, region.regionName),
//                        hospital.dayOfWeek,
//                        hospital.startTime,
//                        hospital.endTime
//                )).from(hospital)
//                .join(hospital.region, region)
//                .where(
//                        region.regionId.eq(regionId),
//
//                )
//                .fetch();
        return null;
    }
}
