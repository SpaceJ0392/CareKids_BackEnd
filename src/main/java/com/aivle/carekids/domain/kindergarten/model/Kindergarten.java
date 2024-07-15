package com.aivle.carekids.domain.kindergarten.model;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Kindergarten extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kindergartenId;

    private String kindergartenName;

    private String kindergartenAddress;

    private String kindergartenNewaddress;

    private String kindergartenPhone;

    @Column(precision = 10, scale = 6)
    private BigDecimal x;

    @Column(precision = 10, scale = 6)
    private BigDecimal y;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kindergarten")
    private List<KindergartenOperateTime> kindergartenOperateTimes = new ArrayList<>();

    @Builder
    public Kindergarten(Long kindergartenId, String kindergartenName, String kindergartenAddress, String kindergartenNewaddress, String kindergartenPhone) {
        this.kindergartenId = kindergartenId;
        this.kindergartenName = kindergartenName;
        this.kindergartenAddress = kindergartenAddress;
        this.kindergartenNewaddress = kindergartenNewaddress;
        this.kindergartenPhone = kindergartenPhone;
    }

    public static Kindergarten createNewKindergarten(KindergartenDetailDto kindergartenDetailDto){
        return Kindergarten.builder()
                .kindergartenName(kindergartenDetailDto.getKindergartenName())
                .kindergartenAddress(kindergartenDetailDto.getKindergartenAddress())
                .kindergartenNewaddress(kindergartenDetailDto.getKindergartenNewaddress())
                .kindergartenPhone(kindergartenDetailDto.getKindergartenPhone())
                .build();
    }

    public void setRegionInfo(Region targetRegion) {
        this.region = targetRegion;
        targetRegion.getKindergartens().add(this);
    }

    public void updateKindergartenInfo(KindergartenDetailDto kindergartenDetailDto) {

        this.kindergartenName = kindergartenDetailDto.getKindergartenName();
        this.kindergartenAddress = kindergartenDetailDto.getKindergartenAddress();
        this.kindergartenNewaddress = kindergartenDetailDto.getKindergartenNewaddress();
        this.kindergartenPhone = kindergartenDetailDto.getKindergartenPhone();

    }

    public void clearOperateTime() {
        kindergartenOperateTimes.forEach(operateTime -> {
            operateTime.setKindergartenInfo(null);
        });
        this.kindergartenOperateTimes.clear();
    }


    public void deletedKindergarten(boolean deleted){
        this.deleted = deleted;
    }

}
