package com.aivle.carekids.domain.notice.repository;

import com.aivle.carekids.domain.notice.models.NoticeUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeUsersRepository extends JpaRepository<NoticeUsers, Long> {
}
