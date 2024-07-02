package com.aivle.carekids.domain.place.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FreeType {
    NO("유료"), YES("무료"), NO_INFO("정보 없음");

    private final String freeType;
}
