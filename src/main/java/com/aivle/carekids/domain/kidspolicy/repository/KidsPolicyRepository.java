package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.kidspolicy.models.KidsPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidsPolicyRepository extends JpaRepository<KidsPolicy, Long>, KidsPolicyRepositoryCustom {
    Page<KidsPolicy> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
