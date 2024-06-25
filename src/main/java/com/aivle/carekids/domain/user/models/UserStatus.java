package com.aivle.carekids.domain.user.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserStatus {
    ADMIN("ADMIN"), USER("USER");

    private final String status;

}
