package com.aivle.carekids.domain.notice.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoticeImg {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeImgId;

    private String noticeImgUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    // * 사용자 정의 메소드 * //
    public void softDeleted(){
        this.deleted = !this.deleted;
    }
}
