package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DevDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long devDomainId;

    private String devDomainType;

    @OneToMany(mappedBy = "devDomain", fetch = FetchType.LAZY)
    private List<PlayInfoDomain> playInfoDomains = new ArrayList<>();
}
