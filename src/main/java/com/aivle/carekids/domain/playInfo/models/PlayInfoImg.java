package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayInfoImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PlayInfoImgId;

    private String playInfoImgUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_info_id")
    private PlayInfo playInfo;

    public void softDeleted() { this.deleted = !this.deleted; }
}
