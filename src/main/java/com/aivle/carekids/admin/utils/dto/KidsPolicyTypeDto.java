package com.aivle.carekids.admin.utils.dto;

import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KidsPolicyTypeDto {
    @JsonProperty("id")
    public KidsPolicyType kidsPolicyType;

    @JsonProperty("kids-policy-type")
    public String kidsPolicyTypeName;
}
