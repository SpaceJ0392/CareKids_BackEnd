package com.aivle.carekids.domain.kidspolicy.models;

import com.aivle.carekids.domain.common.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KidsPolicy extends BaseEntity {
    // 육아 정책 엔티티
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kidsPolicyId;

    @Column(nullable = false, length = 100)
    private String kidsPolicyTitle;

    @Lob
    private String kidsPolicyText;

    private boolean deleted = false;

    @OneToMany(mappedBy = "kidsPolicy", fetch = FetchType.LAZY)
    private List<KidsPolicyImg> kidsPolicyImgs = new ArrayList<>();

    @OneToMany(mappedBy = "kidsPolicy", fetch = FetchType.LAZY)
    private List<KidsPolicyFile> kidsPolicyFiles = new ArrayList<>();

    // * 사용자 정의 메소드 * //
    public void softDeleted(){
        this.deleted = !this.deleted;
        this.getKidsPolicyFiles().forEach(KidsPolicyFile::softDeleted);
        this.getKidsPolicyImgs().forEach(KidsPolicyImg::softDeleted);
    }
}
