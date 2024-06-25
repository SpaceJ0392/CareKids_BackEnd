package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.user.models.Kids;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidsRepository extends JpaRepository<Kids, Long> {
}
