package com.aivle.carekids.domain.kindergarten.repository;

import com.aivle.carekids.domain.kindergarten.model.KindergartenOperateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindergartenOperateTimeRepository extends JpaRepository<KindergartenOperateTime, Long> {
}
