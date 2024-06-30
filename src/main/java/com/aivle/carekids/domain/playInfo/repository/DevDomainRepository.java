package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.playInfo.models.DevDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevDomainRepository extends JpaRepository<DevDomain, Long> {
}
