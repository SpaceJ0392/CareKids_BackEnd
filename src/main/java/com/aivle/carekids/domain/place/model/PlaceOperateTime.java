package com.aivle.carekids.domain.place.model;

import com.aivle.carekids.domain.common.models.OperateDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaceOperateTime extends OperateDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeOperateTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
}
