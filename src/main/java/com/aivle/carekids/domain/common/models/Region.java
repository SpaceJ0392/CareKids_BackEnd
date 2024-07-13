package com.aivle.carekids.domain.common.models;

import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.hospital.model.Hospital;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyRegion;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyRegionAgeTag;
import com.aivle.carekids.domain.place.model.Place;
import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Region {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    @Column(length=10, unique = true)
    private String regionName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private List<Place> place = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private List<Users> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private List<Hospital> hospitals = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private List<KidsPolicyRegionAgeTag> kidsPolicyRegionAgeTags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private List<KidsPolicyRegion> kidsPolicyRegion = new ArrayList<>();

    public void setRegionInfo(RegionDto regionDto){
        this.regionId = regionDto.getRegionId();
        this.regionName = regionDto.getRegionName();
    }

}
