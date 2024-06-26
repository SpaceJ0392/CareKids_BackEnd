package com.aivle.carekids.domain.user.models;

import com.aivle.carekids.domain.common.models.AgeTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Kids {
    // 아이 관련 엔티티
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kidsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_tag_id")
    private AgeTag ageTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @Builder
    public Kids(AgeTag ageTag, User user) {
        this.ageTag = ageTag;
        this.user = user;
    }

    // * 사용자 정의 함수 * //
    public static Kids setKidsInfo(User user, AgeTag ageTag) {

        Kids kids = Kids.builder().ageTag(ageTag).users(user).build();

        user.getKids().add(kids);
        ageTag.getKids().add(kids);

        return kids;
    }
}
