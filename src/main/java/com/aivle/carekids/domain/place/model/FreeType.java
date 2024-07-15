package com.aivle.carekids.domain.place.model;

import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FreeType {
    NO("유료"), YES("무료"), NO_INFO("정보 없음");

    private final String freeType;

    public static FreeType fromFreeTypeString(String freeType) {
        for (FreeType type : FreeType.values()) {
            if (type.getFreeType().equals(freeType)) { return type; }
        }
        throw new IllegalArgumentException("No matching FreeType for description: " + freeType);
    }

}