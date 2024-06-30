package com.aivle.carekids.domain.place.repository;

import com.aivle.carekids.domain.place.model.Mainplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainplaceRepository extends JpaRepository<Mainplace, Long> {
}
