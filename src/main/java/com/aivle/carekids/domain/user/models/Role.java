package com.aivle.carekids.domain.user.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

}
