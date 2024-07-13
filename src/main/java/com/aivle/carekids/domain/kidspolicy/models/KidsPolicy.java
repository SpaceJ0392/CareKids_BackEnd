package com.aivle.carekids.domain.kidspolicy.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.user.models.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kidsPolicyId;

    @Column(nullable = false, length = 100)
    private String kidsPolicyTitle;

    @Lob
    private String kidsPolicyText;

    private String kidsPolicyTarget;

    @Column(length = 500)
    private String kidsPolicyProcess;

    private String kidsPolicyUrl;

    @Enumerated(EnumType.STRING)
    private KidsPolicyType kidsPolicyType;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

//    @OneToMany(mappedBy = "kidsPolicy", fetch = FetchType.LAZY)
//    private List<KidsPolicyRegionAgeTag> kidsPolicyRegionAgeTags = new ArrayList<>();

    @OneToMany(mappedBy = "kidsPolicy", fetch = FetchType.LAZY)
    private List<KidsPolicyRegion> kidsPolicyRegions = new ArrayList<>();

    @OneToMany(mappedBy = "kidsPolicy", fetch = FetchType.LAZY)
    private List<KidsPolicyAgeTag> kidsPolicyAgeTags = new ArrayList<>();

    @Builder
    private KidsPolicy(String kidsPolicyTitle, String kidsPolicyText, String kidsPolicyTarget, String kidsPolicyProcess, String kidsPolicyUrl, KidsPolicyType kidsPolicyType) {
        this.kidsPolicyTitle = kidsPolicyTitle;
        this.kidsPolicyText = kidsPolicyText;
        this.kidsPolicyTarget = kidsPolicyTarget;
        this.kidsPolicyProcess = kidsPolicyProcess;
        this.kidsPolicyUrl = kidsPolicyUrl;
        this.kidsPolicyType = kidsPolicyType;
    }


    // * 사용자 정의 메소드 * //
    //입력에 대한 생성 메소드 필요

    public static KidsPolicy createNewKidsPolicy(KidsPolicyDetailDto kidsPolicyDetailDto) {

        KidsPolicyType kidsPolicyType = KidsPolicyType.fromKidsPolicyTypeString(kidsPolicyDetailDto.getKidsPolicyType());

        return KidsPolicy.builder()
                .kidsPolicyTitle(kidsPolicyDetailDto.getKidsPolicyTitle())
                .kidsPolicyText(kidsPolicyDetailDto.getKidsPolicyText())
                .kidsPolicyTarget(kidsPolicyDetailDto.getKidsPolicyTarget())
                .kidsPolicyProcess(kidsPolicyDetailDto.getKidsPolicyProcess())
                .kidsPolicyUrl(kidsPolicyDetailDto.getKidsPolicyUrl())
                .kidsPolicyType(kidsPolicyType)
                .build();
    }


    public void setUserInfo(Users users) {
        this.users = users;
        users.getKidsPolicys().add(this);
    }

    public void updateKidsPolicyInfo(KidsPolicyDetailDto kidsPolicyDetailDto) {

        KidsPolicyType kidsPolicyType = KidsPolicyType.fromKidsPolicyTypeString(kidsPolicyDetailDto.getKidsPolicyType());

        this.kidsPolicyTitle = kidsPolicyDetailDto.getKidsPolicyTitle();
        this.kidsPolicyText = kidsPolicyDetailDto.getKidsPolicyText();
        this.kidsPolicyTarget = kidsPolicyDetailDto.getKidsPolicyTarget();
        this.kidsPolicyProcess = kidsPolicyDetailDto.getKidsPolicyProcess();
        this.kidsPolicyUrl = kidsPolicyDetailDto.getKidsPolicyUrl();
        this.kidsPolicyType = kidsPolicyType;
    }

    public void clearRegionAgeTag() {

        this.kidsPolicyRegions.forEach(kidsPolicyRegion -> {kidsPolicyRegion.setKidsPolicyRegionInfo(null,null);});
        this.kidsPolicyAgeTags.forEach(kidsPolicyAgeTag -> {kidsPolicyAgeTag.setKidsPolicyAgeTagInfo(null,null);});

        this.kidsPolicyRegions.clear();
        this.kidsPolicyAgeTags.clear();
    }

    public void deletedKidsPolicy(boolean deleted) {
        this.deleted = deleted;
    }
}
