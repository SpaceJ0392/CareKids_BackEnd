package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.playInfo.models.PlayInfoUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayInfoUsersRepository extends JpaRepository<PlayInfoUsers, Long> {
}
