package com.aivle.carekids.domain.hospital.model;

import com.aivle.carekids.domain.common.models.DayType;
import com.aivle.carekids.domain.common.models.OperateDate;
import com.aivle.carekids.domain.hospital.dto.HospitalOperateTimeDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HospitalOperateTime extends OperateDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalOperateTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Builder
    private HospitalOperateTime(DayType dayType, LocalTime startTime, LocalTime endTime) {
        super(dayType, startTime, endTime);

    }


    public static HospitalOperateTime createNewHospitalOperateTime(HospitalOperateTimeDto hospitalOperateTimeDto){

        DayType targetDayType = DayType.fromDayTypeString(hospitalOperateTimeDto.getDayType());

        return HospitalOperateTime.builder()
                .dayType(targetDayType)
                .startTime(hospitalOperateTimeDto.getStartTime())
                .endTime(hospitalOperateTimeDto.getEndTime())
                .build();
    }

    public void setHospitalInfo(Hospital hospital){

        if (hospital == null){
            this.hospital = null;
            return;
        }

        this.hospital = hospital;
        hospital.getHospitalOperateTimes().add(this);
    }
}