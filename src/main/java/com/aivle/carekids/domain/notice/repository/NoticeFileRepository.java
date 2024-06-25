package com.aivle.carekids.domain.notice.repository;

import com.aivle.carekids.domain.notice.models.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {
}
