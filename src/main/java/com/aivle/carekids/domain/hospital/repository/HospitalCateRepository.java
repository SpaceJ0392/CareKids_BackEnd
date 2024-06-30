package com.aivle.carekids.domain.hospital.repository;

import com.aivle.carekids.domain.hospital.model.HospitalCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalCateRepository extends JpaRepository<HospitalCate, Long> {
}
