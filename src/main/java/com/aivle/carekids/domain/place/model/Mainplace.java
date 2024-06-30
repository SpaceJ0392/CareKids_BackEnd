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
public class Mainplace {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mainplaceId;

    @Column(length = 30)
    private String mainplacetype;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainplace")
    private List<PlaceMainplace> placeMainplaces = new ArrayList<>();
}
