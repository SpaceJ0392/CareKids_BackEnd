package com.aivle.carekids.domain.playInfo.repository;

import com.aivle.carekids.domain.playInfo.models.PlayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayInfoRepository extends JpaRepository<PlayInfo, Long>, PlayInfoRepositoryCustom{
    List<PlayInfo> findTop5ByOrderByUpdatedAtDesc();
}
