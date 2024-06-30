package com.aivle.carekids.domain.playInfo.models;

import com.aivle.carekids.domain.common.models.AgeTag;
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
@SQLDelete(sql = "UPDATE play_info SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class PlayInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PlayInfoId;

    @Column(nullable = false, length = 100)
    private String playInfoTitle;

    @Lob
    private String playInfoText;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_tag_id")
    private AgeTag ageTag;

    @OneToMany(mappedBy = "playInfo", fetch = FetchType.LAZY)
    private List<PlayInfoTools> playInfoTools = new ArrayList<>();

    @OneToMany(mappedBy = "playInfo", fetch = FetchType.LAZY)
    private List<PlayInfoDomain> playInfoDomains = new ArrayList<>();

    @OneToMany(mappedBy = "playInfo", fetch = FetchType.LAZY)
    private List<PlayInfoUsers> playInfoUsers = new ArrayList<>();

    // TODO - 입력에 대한 생성 메소드 필요
}
