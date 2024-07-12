package com.aivle.carekids.domain.common.repository;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeTagRepository extends JpaRepository<AgeTag, Long>, AgeTagRepositoryCustom {
    AgeTag findByAgeTagName(String name);
}