package com.aivle.carekids.domain.common.repository;

import com.aivle.carekids.domain.common.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, RegionRepositoryCustom {

}
