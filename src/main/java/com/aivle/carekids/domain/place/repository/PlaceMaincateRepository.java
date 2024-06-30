package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.place.model.PlaceMaincate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceMaincateRepository extends JpaRepository<PlaceMaincate, Long> {
}
