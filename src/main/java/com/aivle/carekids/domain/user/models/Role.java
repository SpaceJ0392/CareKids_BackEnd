package com.aivle.carekids.domain.user.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER"), GUEST("ROLE_GUEST");

    private final String role;

}
