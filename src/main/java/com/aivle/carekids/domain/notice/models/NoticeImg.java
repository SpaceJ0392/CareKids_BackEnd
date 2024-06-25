package com.aivle.carekids.domain.notice.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE notice_img SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class NoticeImg {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeImgId;

    private String noticeImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;
}
