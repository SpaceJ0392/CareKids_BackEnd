package com.aivle.carekids.domain.place.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaceKeyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeKeywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;
}
