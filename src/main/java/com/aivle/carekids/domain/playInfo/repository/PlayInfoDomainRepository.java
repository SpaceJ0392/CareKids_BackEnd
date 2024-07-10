package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.playInfo.model.PlayInfoDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayInfoDomainRepository extends JpaRepository<PlayInfoDomain, Long> {
}
