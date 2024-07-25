package com.aivle.carekids.domain.notice.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE notice SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Notice extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(nullable = false, length = 100)
    private String noticeTitle;

    @Lob
    private String noticeText;

    private String noticeImgUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder
    public Notice(String noticeTitle, String noticeText, String noticeImgUrl) {
        this.noticeTitle = noticeTitle;
        this.noticeText = noticeText;
        this.noticeImgUrl = noticeImgUrl;
    }

    //* 사용자 정의 메소드*//
    public void setUsersInfo(Users users){
        this.users = users;
        users.getNotices().add(this);
    }

    public static Notice createNewNotice(NoticeDto noticeDto){

        return Notice.builder()
                .noticeTitle(noticeDto.getNoticeTitle())
                .noticeText(noticeDto.getNoticeText())
                .noticeImgUrl(noticeDto.getNoticeImgUrl())
                .build();
    }

    public void updateNotice(NoticeDto noticeDto){
        this.noticeTitle = noticeDto.getNoticeTitle();
        this.noticeText = noticeDto.getNoticeText();
        this.noticeImgUrl = noticeDto.getNoticeImgUrl();
    }

    public void setDeletedInfo(boolean deleted) {
        this.deleted = deleted;
    }
}
