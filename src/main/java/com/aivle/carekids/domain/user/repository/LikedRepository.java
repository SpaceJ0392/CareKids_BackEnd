package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.user.models.Liked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedRepository extends JpaRepository<Liked, Long> {
}
