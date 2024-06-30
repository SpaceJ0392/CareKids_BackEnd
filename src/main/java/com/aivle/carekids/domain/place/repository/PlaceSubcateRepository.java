package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.place.model.PlaceSubcate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceSubcateRepository extends JpaRepository<PlaceSubcate, Long> {
}
