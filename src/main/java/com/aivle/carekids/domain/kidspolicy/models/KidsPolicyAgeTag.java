package com.aivle.carekids.domain.kidspolicy.models;

import com.aivle.carekids.domain.common.models.AgeTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KidsPolicyAgeTag {
    // 육아 정보 및 지역 중계 엔티티

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kidsPolicyAgeTagId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kids_policy_id")
    private KidsPolicy kidsPolicy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_tag_id")
    private AgeTag ageTag;

    @Builder
    private KidsPolicyAgeTag(KidsPolicy kidsPolicy, AgeTag ageTag) {
        this.kidsPolicy = kidsPolicy;
        this.ageTag = ageTag;
    }

    public static KidsPolicyAgeTag createNewKidsPolicyAgeTag(KidsPolicy kidsPolicy, AgeTag ageTag) {

        return KidsPolicyAgeTag.builder()
                .ageTag(ageTag)
                .kidsPolicy(kidsPolicy)
                .build();
    }

    public void setKidsPolicyAgeTagInfo(KidsPolicy kidsPolicy, AgeTag ageTag) {

        if (kidsPolicy == null || ageTag == null){
            this.ageTag = null;
            this.kidsPolicy = null;
            return;
        }

        this.ageTag = ageTag;
        ageTag.getKidsPolicyAgeTags().add(this);

        this.kidsPolicy = kidsPolicy;
        kidsPolicy.getKidsPolicyAgeTags().add(this);
    }
}
