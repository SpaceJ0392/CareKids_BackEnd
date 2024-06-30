package com.aivle.carekids.domain.hospital.model;

import com.aivle.carekids.domain.common.models.OperateTime;
import com.aivle.carekids.domain.common.models.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

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

    private String hospitalphone;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    private List<HospitalCateMid> hospitalCateMids = new ArrayList<>();

}
