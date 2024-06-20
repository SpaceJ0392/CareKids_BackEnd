package com.aivle.carekids.domain.common.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Region {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long regionId;

    @Column(length=10, unique = true)
    private String regionName;
}
