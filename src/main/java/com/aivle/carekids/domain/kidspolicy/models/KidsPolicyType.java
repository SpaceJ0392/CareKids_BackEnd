package com.aivle.carekids.domain.kidspolicy.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KidsPolicyType {
    SERVICES_SUPPORT("서비스 지원"), CHILDCARE_SUPPORT("보육료 지원");

    private final String kidsPolicyType;

    public static KidsPolicyType fromKidsPolicyTypeString(String kidsPolicyType) {
        for (KidsPolicyType type : KidsPolicyType.values()) {
            if (type.getKidsPolicyType().equals(kidsPolicyType)) { return type; }
        }
        throw new IllegalArgumentException("No matching KidsPolicyType for description: " + kidsPolicyType);
    }
}
