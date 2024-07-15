package com.aivle.carekids.domain.kidspolicy.models;

import com.aivle.carekids.domain.common.models.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KidsPolicyRegion {
    // 육아 정보 및 지역 중계 엔티티

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kidsPolicyRegionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kids_policy_id")
    private KidsPolicy kidsPolicy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Builder
    private KidsPolicyRegion(KidsPolicy kidsPolicy, Region region) {
        this.kidsPolicy = kidsPolicy;
        this.region = region;// 여기서 초기화
    }

    public static KidsPolicyRegion createNewKidsPolicyRegion(KidsPolicy newKidsPolicy, Region region) {
        return KidsPolicyRegion.builder()
                .kidsPolicy(newKidsPolicy)
                .region(region)
                .build();
    }

    public void setKidsPolicyRegionInfo(KidsPolicy kidsPolicy, Region region){

        if (kidsPolicy == null || region == null){
            this.kidsPolicy = null;
            this.region = null;
            return;
        }

        this.kidsPolicy = kidsPolicy;
        kidsPolicy.getKidsPolicyRegions().add(this);

        this.region = region;
        region.getKidsPolicyRegion().add(this);
    }

}
