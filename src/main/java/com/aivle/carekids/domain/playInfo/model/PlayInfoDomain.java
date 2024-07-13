package com.aivle.carekids.domain.playInfo.model;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicy;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyAgeTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlayInfoDomain {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playInfoDomainId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_info_id")
    private PlayInfo playInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dev_domain_id")
    private DevDomain devDomain;

    @Builder
    public PlayInfoDomain(PlayInfo playInfo, DevDomain devDomain) {
        this.playInfo = playInfo;
        this.devDomain = devDomain;
    }

    public static PlayInfoDomain createNewPlayInfoDomain(PlayInfo playInfo, DevDomain devDomain) {

        return PlayInfoDomain.builder()
                .playInfo(playInfo)
                .devDomain(devDomain)
                .build();
    }

    public void setPlayInfoDomainInfo(PlayInfo playInfo, DevDomain devDomain){

        if (playInfo == null || devDomain == null){
            this.playInfo = null;
            this.devDomain = null;
            return;
        }

        this.playInfo = playInfo;
        playInfo.getPlayInfoDomains().add(this);

        this.devDomain = devDomain;
        devDomain.getPlayInfoDomains().add(this);
    }

}
