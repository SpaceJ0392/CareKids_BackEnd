package com.aivle.carekids.domain.place.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public PlaceKeyword(Place place, Keyword keyword) {
        this.place = place;
        this.keyword = keyword;
    }

    public static PlaceKeyword createNewPlaceKeyword(Place place, Keyword keyword) {
        return PlaceKeyword.builder()
                .place(place)
                .keyword(keyword)
                .build();
    }

    public void setPlaceKeywordInfo(Place place, Keyword keyword){

        if (place == null || keyword == null){
            this.place = null;
            this.keyword = null;
            return;
        }

        this.place = place;
        place.getPlaceKeywords().add(this);

        this.keyword = keyword;
        keyword.getPlaceKeywords().add(this);
    }

}
