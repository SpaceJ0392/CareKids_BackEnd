package com.aivle.carekids.domain.kidspolicy.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE kids_policy_img SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class KidsPolicyImg {
    // 육아 정보 이미지 엔티티

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kidsPolicyImgId;

    private String kidsPolicyImgUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY) // TODO - cascade 고려
    @JoinColumn(name = "kids_policy_id")
    private KidsPolicy kidsPolicy;
}
