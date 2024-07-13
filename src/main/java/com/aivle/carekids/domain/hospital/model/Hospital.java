package com.aivle.carekids.domain.hospital.model;

import com.aivle.carekids.domain.common.models.BaseEntity;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
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
public class Hospital extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;

    private String hospitalName;

    private String hospitalAddress;

    private String hospitalNewaddress;

    private String hospitalPhone;

    private boolean deleted = false;

    @Column(precision = 10, scale = 6)
    private BigDecimal x;

    @Column(precision = 10, scale = 6)
    private BigDecimal y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Enumerated(EnumType.STRING)
    private HospitalType hospitalType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    private List<HospitalOperateTime> hospitalOperateTimes = new ArrayList<>();

    @Builder
    private Hospital(String hospitalName, String hospitalAddress, String hospitalNewaddress, String hospitalPhone, Region region, HospitalType hospitalType) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalNewaddress = hospitalNewaddress;
        this.hospitalPhone = hospitalPhone;
        this.hospitalType = hospitalType;
    }

    public static Hospital createNewHospital(HospitalDetailDto hospitalDetailDto) {

        HospitalType hospitalType = HospitalType.fromHospitalTypeString(hospitalDetailDto.getHospitalType());

        return Hospital.builder()
                .hospitalName(hospitalDetailDto.getHospitalName())
                .hospitalAddress(hospitalDetailDto.getHospitalAddress())
                .hospitalNewaddress(hospitalDetailDto.getHospitalNewaddress())
                .hospitalPhone(hospitalDetailDto.getHospitalPhone())
                .hospitalType(hospitalType)
                .build();
    }

    public void setRegionInfo(Region targetRegion) {
        this.region = targetRegion;
        targetRegion.getHospitals().add(this);
    }

    public void updateHospitalInfo(HospitalDetailDto hospitalDetailDto) {

        HospitalType hospitalType = HospitalType.fromHospitalTypeString(hospitalDetailDto.getHospitalType());

        this.hospitalName = hospitalDetailDto.getHospitalName();
        this.hospitalAddress = hospitalDetailDto.getHospitalAddress();
        this.hospitalNewaddress = hospitalDetailDto.getHospitalNewaddress();
        this.hospitalPhone = hospitalDetailDto.getHospitalPhone();
        this.hospitalType = hospitalType;

    }

    public void clearOperateTime() {
        hospitalOperateTimes.forEach(operateTime -> {
            operateTime.setHospitalInfo(null);
        });
        this.hospitalOperateTimes.clear();
    }

    public void deletedHospital(boolean deleted){
        this.deleted = deleted;
    }
}
