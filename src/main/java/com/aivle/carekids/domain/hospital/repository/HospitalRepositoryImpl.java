package com.aivle.carekids.domain.hospital.repository;

import com.aivle.carekids.domain.common.dto.OperateTimeDto;
import com.aivle.carekids.domain.common.dto.QRegionDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.dto.SearchRegionDto;
import com.aivle.carekids.domain.common.models.DayType;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import com.aivle.carekids.domain.hospital.dto.HospitalOperateTimeDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aivle.carekids.domain.common.models.QRegion.region;
import static com.aivle.carekids.domain.hospital.model.QHospital.hospital;
import static com.aivle.carekids.domain.hospital.model.QHospitalOperateTime.hospitalOperateTime;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepositoryCustom {

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
    public Page<HospitalListDto> findAllByOrderByUpdatedAtDescByPageByRegion(Long regionId, Pageable pageable) {

        List<HospitalListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                HospitalListDto.class,
                                hospital.hospitalId,
                                hospital.hospitalName,
                                hospital.hospitalAddress,
                                hospital.hospitalNewaddress,
                                hospital.hospitalPhone,
                                hospital.hospitalType,
                                Projections.constructor(RegionDto.class, region.regionId, region.regionName)
                        )).from(hospital)
                .join(hospital.region, region)
                .where(regionEq(regionId))
                .orderBy(hospital.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Map<Long, List<HospitalOperateTimeDto>> operateTimeByHospitalList = findOperateTimeByHospitalList(content);
        content.forEach(c -> c.setHospitalOperateTimeDto(operateTimeByHospitalList.get(c.getHospitalId())));


        JPAQuery<Long> countQuery = jpaQueryFactory.select(hospital.count()).from(hospital)
                .join(hospital.region, region)
                .where(regionEq(regionId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    @Override
    public Page<HospitalListDto> findHospitalByFilter(Long regionId, LocalTime now, DayOfWeek today, Pageable pageable) {

        BooleanExpression isInTime = hospitalOperateTime.startTime.loe(now).and(hospitalOperateTime.endTime.goe(now));
        DayType isWeekDay = Objects.equals(today.name(), "SUNDAY") || Objects.equals(today.name(), "SATURDAY") ? DayType.WEEKEND : DayType.WEEKDAY;

        List<HospitalListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                HospitalListDto.class,
                                hospital.hospitalId,
                                hospital.hospitalName,
                                hospital.hospitalType,
                                Projections.constructor(RegionDto.class, region.regionId, region.regionName),
                                Projections.constructor(OperateTimeDto.class, hospitalOperateTime.startTime, hospitalOperateTime.endTime)
                        )).from(hospital)
                .join(hospital.region, region)
                .join(hospital.hospitalOperateTimes, hospitalOperateTime)
                .where(
                        region.regionId.eq(regionId),
                        hospitalOperateTime.dayType.eq(isWeekDay),
                        isInTime
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(hospital.count())
                .from(hospital)
                .join(hospital.hospitalOperateTimes, hospitalOperateTime)
                .where(isInTime);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    @Override
    public HospitalDetailDto findHospialDetail(Long hospitalId) {

        HospitalDetailDto content = jpaQueryFactory.select(Projections.constructor(
                        HospitalDetailDto.class,
                        hospital.hospitalId,
                        hospital.hospitalName,
                        hospital.hospitalAddress,
                        hospital.hospitalNewaddress,
                        hospital.hospitalPhone,
                        Projections.constructor(RegionDto.class, region.regionId, region.regionName),
                        hospital.hospitalType
                )).from(hospital)
                .join(hospital.region, region)
                .where(hospital.hospitalId.eq(hospitalId)).fetchOne();

        Optional<HospitalDetailDto> contentOptional = Optional.ofNullable(content);
        if (contentOptional.isEmpty()) {
            return null;
        }

        content.setHospitalOperateTimes(
                jpaQueryFactory.select(
                                Projections.constructor(
                                        HospitalOperateTimeDto.class,
                                        hospitalOperateTime.dayType,
                                        hospitalOperateTime.startTime,
                                        hospitalOperateTime.endTime,
                                        hospital.hospitalId
                                ))
                        .from(hospitalOperateTime)
                        .where(hospitalOperateTime.hospital.hospitalId.eq(hospitalId)).fetch()
        );

        return content;
    }


    @Override
    public Page<HospitalListDto> searchHospitalByFilter(SearchRegionDto searchRegionDto, Pageable pageable) {

        List<HospitalListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                HospitalListDto.class,
                                hospital.hospitalId,
                                hospital.hospitalName,
                                hospital.hospitalAddress,
                                hospital.hospitalNewaddress,
                                hospital.hospitalPhone,
                                hospital.hospitalType,
                                Projections.constructor(RegionDto.class, region.regionId, region.regionName)
                        )).from(hospital)
                .join(hospital.region, region)
                .where(
                        regionEq(searchRegionDto.getRegionDto().getRegionId()),
                        queryContains(searchRegionDto.getQuery())
                )
                .orderBy(hospital.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<HospitalOperateTimeDto>> operateTimeByHospitalList = findOperateTimeByHospitalList(content);
        content.forEach(c -> c.setHospitalOperateTimeDto(operateTimeByHospitalList.get(c.getHospitalId())));


        JPAQuery<Long> countQuery = jpaQueryFactory.select(hospital.count()).from(hospital)
                .join(hospital.region, region)
                .where(
                        regionEq(searchRegionDto.getRegionDto().getRegionId()),
                        queryContains(searchRegionDto.getQuery())
                );


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    private BooleanExpression regionEq(Long regionId) {
        return isEmpty(regionId) || regionId == 26 ? null : hospital.region.regionId.eq(regionId);
    }


    private BooleanExpression queryContains(String query) {
        return isEmpty(query) ? null : hospital.hospitalName.containsIgnoreCase(query);
    }


    private Map<Long, List<HospitalOperateTimeDto>> findOperateTimeByHospitalList(List<HospitalListDto> content) {

        List<Long> hospitalIdList = content.stream().map(HospitalListDto::getHospitalId).toList();

        return jpaQueryFactory.select(
                        Projections.constructor(
                                HospitalOperateTimeDto.class,
                                hospitalOperateTime.dayType,
                                hospitalOperateTime.startTime,
                                hospitalOperateTime.endTime,
                                hospital.hospitalId
                        ))
                .from(hospitalOperateTime)
                .where(hospitalOperateTime.hospital.hospitalId.in(hospitalIdList))
                .fetch().stream().collect(Collectors.groupingBy(HospitalOperateTimeDto::getHospitalId));
    }

}
