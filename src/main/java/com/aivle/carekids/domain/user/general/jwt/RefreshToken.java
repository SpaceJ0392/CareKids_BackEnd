package com.aivle.carekids.domain.user.general.jwt;

import lombok.Data;

@Data
public class RefreshToken {
    private String token;
    private Long usersId;

    public RefreshToken(String token, Long usersId) {
        this.token = token;
        this.usersId = usersId;
    }
}