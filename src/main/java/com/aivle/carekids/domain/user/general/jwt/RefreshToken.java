package com.aivle.carekids.domain.user.general.jwt;


import lombok.Data;

@Data
public class RefreshToken {

    private Long usersId;

    private String token;

    public RefreshToken(Long usersId, String token) {
        this.usersId = usersId;
        this.token = token;
    }
}