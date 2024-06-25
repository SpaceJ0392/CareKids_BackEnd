package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE play_info_img SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class PlayInfoImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PlayInfoImgId;

    private String playInfoImgUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_info_id")
    private PlayInfo playInfo;
}
