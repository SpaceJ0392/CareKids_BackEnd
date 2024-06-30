package com.aivle.carekids.domain.hospital.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HospitalCate {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hospitalCateId;

    private String hospitalCateType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospitalCate")
    private List<HospitalCateMid> hospitalCateMids = new ArrayList<>();
}
