package com.aivle.carekids.domain.playInfo.model;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.user.models.Users;
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
@SQLDelete(sql = "UPDATE play_info SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class PlayInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long playInfoId;

    @Column(nullable = false, length = 100)
    private String playInfoTitle;

    @Lob
    private String playInfoText;

    private String playInfoTools;

    private String playInfoRecommendAge;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_tag_id")
    private AgeTag ageTag;

    @OneToMany(mappedBy = "playInfo", fetch = FetchType.LAZY)
    private List<PlayInfoDomain> playInfoDomains = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    // TODO - 입력에 대한 생성 메소드 필요
}
