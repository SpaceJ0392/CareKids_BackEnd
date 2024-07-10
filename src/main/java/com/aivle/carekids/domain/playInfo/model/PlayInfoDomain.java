package com.aivle.carekids.domain.playInfo.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlayInfoDomain {

    @EmbeddedId
    private PlayInfoDomainId playInfoDomainId;

    @MapsId("playInfoId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_info_id")
    private PlayInfo playInfo;

    @MapsId("devDomainId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dev_domain_id")
    private DevDomain devDomain;

}
