package com.aivle.carekids.domain.playInfo.models;

import com.aivle.carekids.domain.user.models.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlayInfoUsers {
    //복합 ID
    @EmbeddedId
    private PlayInfoUsersId playInfoUsersId;

    @MapsId("usersId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @MapsId("playInfoId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_info_id")
    private PlayInfo playInfo;
}
