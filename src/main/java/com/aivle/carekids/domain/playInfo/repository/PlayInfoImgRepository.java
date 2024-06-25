package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.playInfo.models.PlayInfoImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayInfoImgRepository extends JpaRepository<PlayInfoImg, Long> {
}
