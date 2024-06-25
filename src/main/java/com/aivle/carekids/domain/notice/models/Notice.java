package com.aivle.carekids.domain.notice.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notice extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeId;

    @Column(nullable = false, length = 100)
    private String noticeTitle;

    private boolean deleted = false;

    @Lob
    private String noticeText;

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeImg> noticeImgs = new ArrayList<>();

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeFile> noticeFiles = new ArrayList<>();

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeUsers> noticeUsers = new ArrayList<>();

    // * 사용자 정의 메소드 * //
    public void softDeleted(){
        this.deleted = !this.deleted;
        this.getNoticeFiles().forEach(NoticeFile::softDeleted);
        this.getNoticeImgs().forEach(NoticeImg::softDeleted);
    }

    // TODO - 입력에 대한 생성 메소드 필요

}
