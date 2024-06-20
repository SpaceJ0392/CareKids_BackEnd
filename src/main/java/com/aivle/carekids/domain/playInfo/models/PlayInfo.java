package com.aivle.carekids.domain.playInfo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PlayInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PlayInfoId;
}
