package com.aivle.carekids.domain.kindergarten.repository;

import com.aivle.carekids.domain.kindergarten.model.Kindergarten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindergartenRepository extends JpaRepository<Kindergarten, Long>, KindergartenRepositoryCustom {
}
