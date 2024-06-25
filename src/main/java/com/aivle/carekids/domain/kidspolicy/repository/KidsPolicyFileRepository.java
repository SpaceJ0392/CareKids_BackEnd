package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidsPolicyFileRepository extends JpaRepository<KidsPolicyFile, Long> {
}
