package com.aivle.carekids.domain.playInfo.models;

import com.aivle.carekids.domain.common.models.AgeTag;
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
    private List<PlayInfoImg> playInfoImgs = new ArrayList<>();

    @OneToMany(mappedBy = "playInfo", fetch = FetchType.LAZY)
    private List<PlayInfoUsers> playInfoUsers = new ArrayList<>();

    // * 사용자 정의 메소드 * //
    public void softDeleted(){
        this.deleted = !this.deleted;
        this.playInfoImgs.forEach(PlayInfoImg::softDeleted);
    }

    // TODO - 입력에 대한 생성 메소드 필요
}
