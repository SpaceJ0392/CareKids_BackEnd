package com.aivle.carekids.domain.user.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Kids {
    // 아이 관련 엔티티
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kidsId;
}
