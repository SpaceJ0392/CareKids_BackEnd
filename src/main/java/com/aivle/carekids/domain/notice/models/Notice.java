package com.aivle.carekids.domain.notice.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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

    // TODO - 입력에 대한 생성 메소드 필요

}
