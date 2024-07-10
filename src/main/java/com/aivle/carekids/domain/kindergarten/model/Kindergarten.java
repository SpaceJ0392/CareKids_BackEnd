package com.aivle.carekids.domain.kindergarten.model;

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
public class Kindergarten extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kindergartenId;

    private String kindergartenName;

    private String kindergartenAddress;

    private String kindergartenNewaddress;

    private String kindergartenPhone;

    @Column(precision = 10, scale = 6)
    private BigDecimal x;

    @Column(precision = 10, scale = 6)
    private BigDecimal y;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kindergarten")
    private List<KindergartenOperateTime> kindergartenOperateTimes = new ArrayList<>();

}
