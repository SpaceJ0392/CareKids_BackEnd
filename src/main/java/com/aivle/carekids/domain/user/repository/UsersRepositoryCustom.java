package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.user.dto.UsersDetailDto;

import java.util.Optional;

public interface UsersRepositoryCustom {
    Optional<UsersDetailDto> findUsersDetailWithRegionAndKids(Long usersId);
}
