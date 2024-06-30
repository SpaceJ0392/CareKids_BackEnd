package com.aivle.carekids.domain.hospital.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class HospitalCateMidId implements Serializable {
    private Long hospitalId;
    private Long hospitalCateId;
}
