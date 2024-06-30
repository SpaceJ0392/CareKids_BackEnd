package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.place.model.PlaceKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceKeywordRepository extends JpaRepository<PlaceKeyword, Long> {
}
