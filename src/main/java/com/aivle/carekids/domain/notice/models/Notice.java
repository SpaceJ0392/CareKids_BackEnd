package com.aivle.carekids.domain.notice.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE notice SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Notice extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeId;

    @Column(nullable = false, length = 100)
    private String noticeTitle;

    @Lob
    private String noticeText;

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeImg> noticeImgs = new ArrayList<>();

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeFile> noticeFiles = new ArrayList<>();

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeUsers> noticeUsers = new ArrayList<>();

    // TODO - 입력에 대한 생성 메소드 필요

}
