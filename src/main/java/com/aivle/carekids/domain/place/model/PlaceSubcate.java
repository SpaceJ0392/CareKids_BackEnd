package com.aivle.carekids.domain.place.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaceSubcate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeSubcateId;

    private String placeSubcateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_maincate_id")
    private PlaceMaincate placeMaincate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "placeSubcate")
    private List<PlaceCate> placeCates = new ArrayList<>();
}
