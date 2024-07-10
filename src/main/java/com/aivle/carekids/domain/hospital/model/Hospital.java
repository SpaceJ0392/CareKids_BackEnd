package com.aivle.carekids.domain.hospital.model;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.common.models.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Hospital extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hospitalId;

    private String hospitalName;

    private String hospitalAddress;

    private String hospitalNewaddress;

    private String hospitalPhone;

    private boolean deleted = false;

    @Column(precision = 10, scale = 6)
    private BigDecimal x;

    @Column(precision = 10, scale = 6)
    private BigDecimal y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Enumerated(EnumType.STRING)
    private HospitalType hospitalType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    private List<HospitalOperateTime> hospitalOperateTimes = new ArrayList<>();

}
