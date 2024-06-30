package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlayInfoTools {

    @EmbeddedId
    private PlayInfoToolsId playInfoToolsId;

    @MapsId("playInfoId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_info_id")
    private PlayInfo playInfo;

    @MapsId("playToolsId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_tools_id")
    private PlayTools playTools;

}
