package com.aivle.carekids.domain.kidspolicy.models;

import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KidsPolicyUsers {
    // 사용자, 욱아정보 중계 엔티티
    @EmbeddedId
    private KidsPolicyUsersId kidsPolicyUsersId;

    @MapsId("kidsPolicyId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kids_policy_id")
    private KidsPolicy kidsPolicy;

    @MapsId("usersId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;
}
