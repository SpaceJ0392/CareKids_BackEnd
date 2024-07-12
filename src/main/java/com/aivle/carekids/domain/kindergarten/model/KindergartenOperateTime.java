package com.aivle.carekids.domain.kindergarten.model;

import com.aivle.carekids.domain.common.models.DayType;
import com.aivle.carekids.domain.common.models.OperateDate;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kindergartenOperateTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kindergarten_id")
    private Kindergarten kindergarten;

}
