package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.place.model.PlaceMainplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceMainplaceRepository extends JpaRepository<PlaceMainplace, Long> {
}
