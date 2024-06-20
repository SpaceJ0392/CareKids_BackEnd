package com.aivle.carekids.domain.user.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    // 사용자 정보 엔티티
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long usersId;
}
