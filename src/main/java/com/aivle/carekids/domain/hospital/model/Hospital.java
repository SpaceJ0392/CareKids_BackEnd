package com.aivle.carekids.domain.hospital.model;

import com.aivle.carekids.domain.common.models.OperateTime;
import com.aivle.carekids.domain.common.models.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Hospital extends OperateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hospitalId;

    private String hospitalName;

    private String hospitalAddress;

    private String hospitalNewaddress;

    private String hospitalphone;

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

}
