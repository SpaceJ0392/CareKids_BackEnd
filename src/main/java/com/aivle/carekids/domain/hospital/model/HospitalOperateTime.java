package com.aivle.carekids.domain.hospital.model;

import com.aivle.carekids.domain.common.models.OperateDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HospitalOperateTime extends OperateDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hospitalOperateTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}