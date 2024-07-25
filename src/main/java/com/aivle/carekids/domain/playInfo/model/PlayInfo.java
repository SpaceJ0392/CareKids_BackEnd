package com.aivle.carekids.domain.playInfo.model;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDetailDto;
import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playInfoId;

    @Column(nullable = false, length = 100)
    private String playInfoTitle;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
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

    @Builder
    public PlayInfo(Long playInfoId, String playInfoTitle, String playInfoText, String playInfoTools, String playInfoRecommendAge) {
        this.playInfoId = playInfoId;
        this.playInfoTitle = playInfoTitle;
        this.playInfoText = playInfoText;
        this.playInfoTools = playInfoTools;
        this.playInfoRecommendAge = playInfoRecommendAge;
    }

    //* 사용자 정의 메소드 *//
    public void setUserInfo(Users targetUsers) {
        this.users = targetUsers;
        targetUsers.getPlayInfoUsers().add(this);
    }

    public void setAgeTagInfo(AgeTag targetAgeTag) {
        this.ageTag = targetAgeTag;
        targetAgeTag.getPlayInfos().add(this);
    }

    public static PlayInfo createNewPlayInfo(PlayInfoDetailDto playInfoDetailDto){
        return PlayInfo.builder()
                .playInfoTitle(playInfoDetailDto.getPlayInfoTitle())
                .playInfoText(playInfoDetailDto.getPlayInfoText())
                .playInfoTools(playInfoDetailDto.getPlayInfoTools())
                .playInfoRecommendAge(playInfoDetailDto.getPlayInfoRecommendAge())
                .build();
    }

    public void updatePlayInfo(PlayInfoDetailDto playInfoDetailDto) {

        this.playInfoTitle = playInfoDetailDto.getPlayInfoTitle();
        this.playInfoText = playInfoDetailDto.getPlayInfoText();
        this.playInfoTools = playInfoDetailDto.getPlayInfoTools();
        this.playInfoRecommendAge = playInfoDetailDto.getPlayInfoRecommendAge();

    }

    public void clearDevDomains() {
        this.playInfoDomains.forEach(PlayInfoDomain -> {PlayInfoDomain.setPlayInfoDomainInfo(null,null);});

        this.playInfoDomains.clear();
    }

    public void deletedPlayInfo(boolean deleted){
        this.deleted = deleted;
    }
}
