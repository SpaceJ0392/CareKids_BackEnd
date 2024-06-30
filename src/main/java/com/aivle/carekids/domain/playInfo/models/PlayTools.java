package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlayTools {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long playToolsId;

    private String playToolsName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playTools")
    private List<PlayInfoTools> playInfoTools = new ArrayList<>();
}
