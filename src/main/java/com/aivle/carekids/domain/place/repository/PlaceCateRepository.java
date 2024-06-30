package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.place.model.PlaceCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceCateRepository extends JpaRepository<PlaceCate, Long> {
}
