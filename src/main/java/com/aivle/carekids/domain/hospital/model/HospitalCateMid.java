package com.aivle.carekids.domain.hospital.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HospitalCateMid {

    @EmbeddedId
    private HospitalCateMidId hospitalCateMidId;

    @MapsId("hospitalId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @MapsId("hospitalCateId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_cate_id")
    private HospitalCate hospitalCate;

}
