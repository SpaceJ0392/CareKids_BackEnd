package com.aivle.carekids.admin.utils.controller;

import com.aivle.carekids.admin.utils.dto.HospitalTypeDto;
import com.aivle.carekids.admin.utils.dto.KidsPolicyTypeDto;
import com.aivle.carekids.admin.utils.dto.OperateDateTypeDto;
import com.aivle.carekids.domain.common.models.DayType;
import com.aivle.carekids.domain.hospital.model.HospitalType;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyType;
import com.aivle.carekids.domain.playInfo.dto.DevDomainDto;
import com.aivle.carekids.domain.playInfo.model.DevDomain;
import com.aivle.carekids.domain.playInfo.repository.DevDomainRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final DevDomainRepository devDomainRepository;
    private final ModelMapper dtoModelMapper;

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


    @GetMapping("/kidsPolicy-type")
    public ResponseEntity<?> displayKidsPolicyType(){

        List<KidsPolicyTypeDto> kidsPolicyTypeDtos = Arrays.stream(KidsPolicyType.values())
                .map(kidsPolicyType -> new KidsPolicyTypeDto(kidsPolicyType, kidsPolicyType.getKidsPolicyType())).toList();

        return ResponseEntity.ok(kidsPolicyTypeDtos);
    }


    @GetMapping("/dev-domain")
    public ResponseEntity<?> displayDevDomain(){

        List<DevDomain> content = devDomainRepository.findAll();
        List<DevDomainDto> devDomainList = content.stream()
                .map(devDomain -> dtoModelMapper.map(devDomain, DevDomainDto.class)).toList();

        return ResponseEntity.ok(devDomainList);
    }

}
