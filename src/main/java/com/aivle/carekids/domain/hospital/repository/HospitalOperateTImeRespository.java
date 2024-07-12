package com.aivle.carekids.domain.hospital.repository;

import com.aivle.carekids.domain.hospital.model.HospitalOperateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalOperateTImeRespository extends JpaRepository<HospitalOperateTime, Long> {
}
