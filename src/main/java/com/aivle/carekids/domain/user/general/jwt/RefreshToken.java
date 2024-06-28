package com.aivle.carekids.domain.user.general.jwt;

import lombok.Data;

@Data
public class RefreshToken {
    private String token;
    private Long userId;

    public RefreshToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }
}