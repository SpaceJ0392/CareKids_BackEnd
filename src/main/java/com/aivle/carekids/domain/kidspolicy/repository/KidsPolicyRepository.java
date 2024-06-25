package com.aivle.carekids.domain.kidspolicy.repository;

import com.aivle.carekids.domain.kidspolicy.models.KidsPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KidsPolicyRepository extends JpaRepository<KidsPolicy, Long> {
    List<KidsPolicy> findTop5ByOrderByUpdatedAt();
}
