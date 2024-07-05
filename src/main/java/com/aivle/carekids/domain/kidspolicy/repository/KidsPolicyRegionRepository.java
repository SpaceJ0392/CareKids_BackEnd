package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyRegionAgeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidsPolicyRegionRepository extends JpaRepository<KidsPolicyRegionAgeTag, Long> {
}
