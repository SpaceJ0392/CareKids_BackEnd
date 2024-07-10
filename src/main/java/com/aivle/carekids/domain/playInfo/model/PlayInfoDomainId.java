package com.aivle.carekids.domain.playInfo.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlayInfoDomainId implements Serializable {
    private Long playInfoId;
    private Long devDomainId;
}
