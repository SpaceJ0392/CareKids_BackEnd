package com.aivle.carekids.domain.user.general.jwt;


import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
public class RefreshToken {
    private Long usersId;
    private String token;

    public RefreshToken(Long usersId, String token) {
        this.usersId = usersId;
        this.token = token;
    }
}