package com.aivle.carekids.domain.place.repository;


import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.dto.SearchRegionCateDto;
import com.aivle.carekids.domain.place.dto.PlaceDetailDto;
import com.aivle.carekids.domain.place.dto.PlaceKeywordDto;
import com.aivle.carekids.domain.place.dto.PlaceListDto;
import com.aivle.carekids.domain.place.dto.PlaceSubcateDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static com.aivle.carekids.domain.place.model.QPlace.place;
import static com.aivle.carekids.domain.place.model.QPlaceCate.placeCate;
import static com.aivle.carekids.domain.place.model.QPlaceKeyword.placeKeyword;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PlaceListDto> findAllByOrderByUpdatedAtDescByPageByRegion(Long regionId, Pageable pageable) {
        List<PlaceListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                PlaceListDto.class,
                                place.placeId,
                                place.placeName,
                                place.placeImgUrl,
                                place.placeAddress,
                                place.placeNewAddress,
                                place.placeOperateTime,
                                Projections.constructor(PlaceSubcateDto.class, placeCate.placeSubcate.placeSubcateId, placeCate.placeSubcate.placeSubcateName)
                        )).from(place)
                .join(place.placeCates, placeCate)
                .where(regionEq(regionId))
                .orderBy(place.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<PlaceKeywordDto>> keywordsByPlaceList = findKeywordsByPlaceList(content);
        content.forEach(c -> c.setPlaceKeywords(keywordsByPlaceList.get(c.getPlaceId())));

        JPAQuery<Long> countQuery = jpaQueryFactory.select(place.count()).from(place)
                .join(place.placeCates, placeCate)
                .where(regionEq(regionId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    private BooleanExpression regionEq(Long regionId) {
        return isEmpty(regionId) ? null : place.region.regionId.eq(regionId);
    }

    private Map<Long, List<PlaceKeywordDto>> findKeywordsByPlaceList(List<PlaceListDto> content) {
        List<Long> placeIdList = content.stream().map(PlaceListDto::getPlaceId).toList();


        return jpaQueryFactory.select(
                        Projections.constructor(
                                PlaceKeywordDto.class,
                                place.placeId,
                                placeKeyword.keyword.keywordId,
                                placeKeyword.keyword.keywordName
                        ))
                .from(placeKeyword)
                .where(placeKeyword.place.placeId.in(placeIdList))
                .fetch().stream().collect(Collectors.groupingBy(PlaceKeywordDto::getPlaceId));

    }

    @Override
    public PlaceDetailDto findPlaceDetail(Long placeId) {
        PlaceDetailDto content = jpaQueryFactory.select(Projections.constructor(
                        PlaceDetailDto.class,
                        place.placeId,
                        place.placeName,
                        place.placeImgUrl,
                        place.placeAddress,
                        place.placeNewAddress,
                        place.placePhone,
                        place.placeType.stringValue(),
                        place.placeParking.stringValue(),
                        place.placeFree.stringValue(),
                        place.placeOperateTime,
                        Projections.constructor(RegionDto.class, region.regionId, region.regionName),
                        Projections.constructor(PlaceSubcateDto.class, placeCate.placeSubcate.placeSubcateId, placeCate.placeSubcate.placeSubcateName)
                )).from(place)
                .join(place.region, region)
                .join(place.placeCates, placeCate)
                .where(place.placeId.eq(placeId)).fetchOne();

        Optional<PlaceDetailDto> contentOptional = Optional.ofNullable(content);
        if (contentOptional.isEmpty()) {
            return null;
        }

        content.setPlaceKeywords(
                jpaQueryFactory.select(
                                Projections.constructor(
                                        PlaceKeywordDto.class,
                                        place.placeId,
                                        placeKeyword.keyword.keywordId,
                                        placeKeyword.keyword.keywordName
                                ))
                        .from(placeKeyword)
                        .where(placeKeyword.place.placeId.eq(placeId)).fetch()
        );

        return content;
    }


    @Override
    public Page<PlaceListDto> searchPlaceByFilter(SearchRegionCateDto searchRegionCateDto, Pageable pageable) {
        List<PlaceListDto> content = jpaQueryFactory.select(
                        Projections.constructor(
                                PlaceListDto.class,
                                place.placeId,
                                place.placeName,
                                place.placeImgUrl,
                                place.placeAddress,
                                place.placeNewAddress,
                                place.placeOperateTime,
                                Projections.constructor(PlaceSubcateDto.class, placeCate.placeSubcate.placeSubcateId, placeCate.placeSubcate.placeSubcateName)
                        )).from(place)
                .join(place.placeCates, placeCate)
                .where(
                        regionEq(searchRegionCateDto.getRegionDto().getRegionId()),
                        subcateEq(searchRegionCateDto.getSubcateDto().getPlaceSubcateName()),
                        queryContains(searchRegionCateDto.getQuery())
                )
                .orderBy(place.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<PlaceKeywordDto>> keywordsByPlaceList = findKeywordsByPlaceList(content);
        content.forEach(c -> c.setPlaceKeywords(keywordsByPlaceList.get(c.getPlaceId())));

        JPAQuery<Long> countQuery = jpaQueryFactory.select(place.count()).from(place)
                .join(place.placeCates, placeCate)
                .where(
                        regionEq(searchRegionCateDto.getRegionDto().getRegionId()),
                        subcateEq(searchRegionCateDto.getSubcateDto().getPlaceSubcateName()),
                        queryContains(searchRegionCateDto.getQuery())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression subcateEq(String subcateName) {
        return isEmpty(subcateName) ? null : placeCate.placeSubcate.placeSubcateName.eq(subcateName);
    }

    private BooleanExpression queryContains(String query) {
        return isEmpty(query) ? null : place.placeName.containsIgnoreCase(query);
    }
}
