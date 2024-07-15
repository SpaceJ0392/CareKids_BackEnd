package com.aivle.carekids.domain.kindergarten.model;

import com.aivle.carekids.domain.common.models.DayType;
import com.aivle.carekids.domain.common.models.OperateDate;
import com.aivle.carekids.domain.hospital.dto.HospitalOperateTimeDto;
import com.aivle.carekids.domain.hospital.model.Hospital;
import com.aivle.carekids.domain.hospital.model.HospitalOperateTime;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenOperateTimeDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KindergartenOperateTime extends OperateDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kindergartenOperateTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kindergarten_id")
    private Kindergarten kindergarten;

    @Builder
    private KindergartenOperateTime(DayType dayType, LocalTime startTime, LocalTime endTime) {
        super(dayType, startTime, endTime);

    }

    public static KindergartenOperateTime createNewKindergartenOperateTime(KindergartenOperateTimeDto kindergartenOperateTimeDto){

        DayType targetDayType = DayType.fromDayTypeString(kindergartenOperateTimeDto.getDayType());

        return KindergartenOperateTime.builder()
                .dayType(targetDayType)
                .startTime(kindergartenOperateTimeDto.getStartTime())
                .endTime(kindergartenOperateTimeDto.getEndTime())
                .build();
    }

    public void setKindergartenInfo(Kindergarten kindergarten){

        if (kindergarten == null){
            this.kindergarten = null;
            return;
        }

        this.kindergarten = kindergarten;
        kindergarten.getKindergartenOperateTimes().add(this);
    }

}
