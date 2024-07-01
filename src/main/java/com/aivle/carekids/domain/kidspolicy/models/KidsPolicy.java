package com.aivle.carekids.domain.kidspolicy.models;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE kids_policy SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class KidsPolicy extends BaseEntity {
    // 육아 정책 엔티티
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kidsPolicyId;

    @Column(nullable = false, length = 100)
    private String kidsPolicyTitle;

    @Lob
    private String kidsPolicyText;

    private String kidsPolicyUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_tag_id")
    private AgeTag ageTag;

    @OneToMany(mappedBy = "kidsPolicy", fetch = FetchType.LAZY)
    private List<KIdsPolicyRegion> kIdsPolicyRegions = new ArrayList<>();

    // * 사용자 정의 메소드 * //
    // TODO - 입력에 대한 생성 메소드 필요
}
