package com.aivle.carekids.domain.user.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Liked {
    //관심 목록 관련 엔티티
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Users users;

    // TODO - 장소 테이블 추가시, 추가.
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "place_id")
    // private Place place;
}