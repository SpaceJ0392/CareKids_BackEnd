package com.aivle.carekids.domain.hospital.repository;

import com.aivle.carekids.domain.hospital.model.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalRepositoryCustom {
    Page<Hospital> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
