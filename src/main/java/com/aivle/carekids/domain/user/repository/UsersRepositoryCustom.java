package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.user.dto.UsersDto;

import java.util.Optional;

public interface UsersRepositoryCustom {
    Optional<UsersDto> findUsersDetailWithRegionAndKids(Long usersId);
}
