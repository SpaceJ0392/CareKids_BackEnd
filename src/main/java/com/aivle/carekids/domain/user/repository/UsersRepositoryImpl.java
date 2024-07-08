package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.aivle.carekids.domain.common.models.QAgeTag.ageTag;
import static com.aivle.carekids.domain.common.models.QRegion.region;
import static com.aivle.carekids.domain.user.models.QKids.kids;
import static com.aivle.carekids.domain.user.models.QUsers.users;

@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UsersDetailDto> findUsersDetailWithRegionAndKids(Long usersId) {
        // toOne은 한번에
        UsersDetailDto usersDetailDto = jpaQueryFactory.select(
                        Projections.constructor(
                                UsersDetailDto.class, users.usersId, users.usersEmail, users.usersNickname,
                                Projections.constructor(RegionDto.class, region.regionId, region.regionName)))
                .from(users)
                .join(users.region, region)
                .where(users.usersId.eq(usersId))
                .fetchOne();

        Optional<UsersDetailDto> usersDtoOptional = Optional.ofNullable(usersDetailDto);
        if (usersDtoOptional.isEmpty()) { return Optional.empty(); }

        // ToMany는 나중에 묶어서
        List<AgeTagDto> kidsAgeTagList = jpaQueryFactory.select(
                Projections.constructor(AgeTagDto.class, ageTag.ageTagId, ageTag.ageTagName)
                ).from(kids)
                .join(kids.ageTag, ageTag)
                .where(kids.users.usersId.eq(usersId))
                .fetch();

        usersDtoOptional.get().setUsersAgeTagDtos(kidsAgeTagList);
        return usersDtoOptional;
    }
}
