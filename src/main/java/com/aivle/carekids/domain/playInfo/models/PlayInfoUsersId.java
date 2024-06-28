package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlayInfoUsersId implements Serializable {
    private Long userId;
    private Long playInfoId;
}
