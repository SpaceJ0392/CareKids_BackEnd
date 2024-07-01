package com.aivle.carekids.domain.hospital.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum HospitalType {

    OCPP("경증 소아환자 외래진료 "), ETPP("중증 응급 소아 환자 진료"),
    UCPP("준응급 소아환자 진료"), PCPP("경증 소아환자 일차진료");

    private final String hospitalCateType;
}
