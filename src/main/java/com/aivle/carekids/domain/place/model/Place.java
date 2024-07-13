package com.aivle.carekids.domain.place.model;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.common.models.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE place SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Place extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @Column(length = 100, nullable = false)
    private String placeName;

    @Column(length = 500)
    private String placeImgUrl;

    @Column(length = 100)
    private String placeAddress;

    private String placeNewAddress;

    @Column(precision = 10, scale = 6)
    private BigDecimal placeX;

    @Column(precision = 10, scale = 6)
    private BigDecimal placeY;

    @Column(length = 70)
    private String placePhone;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @Enumerated(EnumType.STRING)
    private ParkingType placeParking;

    @Enumerated(EnumType.STRING)
    private FreeType placeFree;

    private String placeOperateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    private boolean deleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private List<PlaceCate> placeCates = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private List<PlaceKeyword> placeKeywords = new ArrayList<>();

}
