package com.aivle.carekids.domain.kidspolicy.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class KidsPolicyAgeTagId implements Serializable {

    private Long kidsPolicyId;
    private Long ageTagId;
}
