package com.aivle.carekids.domain.kidspolicy.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KidsPolicyImg {
    // 육아 정보 이미지 엔티티

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kidsPolicyImgId;

    private String kidsPolicyImgUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY) // TODO - cascade 고려
    @JoinColumn(name = "kids_policy_id")
    private KidsPolicy kidsPolicy;

    // * 사용자 정의 메소드 * //
    public void softDeleted(){
        this.deleted = !this.deleted;
    }
}
