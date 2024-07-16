package com.aivle.carekids.domain.notice.repository;

import com.aivle.carekids.domain.notice.models.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findTop5ByOrderByUpdatedAtDesc();
    Page<Notice> findAllByOrderByNoticeIdDesc(Pageable pageable);
}
