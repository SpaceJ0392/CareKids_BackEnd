package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlayInfoDomainId implements Serializable {
    private Long playInfoId;
    private Long devDomainId;
}
