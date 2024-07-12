package com.aivle.carekids.admin.utils.controller;

import com.aivle.carekids.admin.utils.dto.HospitalTypeDto;
import com.aivle.carekids.admin.utils.dto.OperateDateTypeDto;
import com.aivle.carekids.domain.common.models.DayType;
import com.aivle.carekids.domain.hospital.model.HospitalType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UtilsController {

    @GetMapping("/hospital-type")
    public ResponseEntity<?> displayHospitalType(){

        List<HospitalTypeDto> hospitalTypeDtos = Arrays.stream(HospitalType.values())
                .map(hospitalType -> new HospitalTypeDto(hospitalType, hospitalType.getHospitalType())).toList();

        return ResponseEntity.ok(hospitalTypeDtos);
    }

    @GetMapping("/operate-day")
    public ResponseEntity<?> displayOperateDay(){

        List<OperateDateTypeDto> operateDateTypeDtos = Arrays.stream(DayType.values())
                .map(dayType -> new OperateDateTypeDto(dayType, dayType.getDayType())).toList();

        return ResponseEntity.ok(operateDateTypeDtos);
    }
}
