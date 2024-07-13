package com.aivle.carekids.domain.playInfo.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

}
