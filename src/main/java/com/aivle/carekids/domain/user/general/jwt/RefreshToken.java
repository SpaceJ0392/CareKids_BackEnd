package com.aivle.carekids.domain.user.general.jwt;


import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
public class RefreshToken {
    private Long usersId;
    private String token;

    @Column(columnDefinition = "boolean default false")
    private boolean expired; // 토큰 만료

    @Column(columnDefinition = "boolean default false")
    private boolean revoked; // 토큰 폐지

    public RefreshToken(Long usersId, String token) {
        this.usersId = usersId;
        this.token = token;
    }
}