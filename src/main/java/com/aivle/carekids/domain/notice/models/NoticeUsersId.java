package com.aivle.carekids.domain.notice.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class NoticeUsersId {
    private Long noticeId;
    private Long usersId;
}
