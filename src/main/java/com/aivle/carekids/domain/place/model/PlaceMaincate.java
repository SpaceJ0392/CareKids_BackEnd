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
public class PlaceMaincate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeMaincateId;

    private String placeMaincateName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "placeMaincate")
    private List<PlaceSubcate> placeSubcates = new ArrayList<>();
}
