package com.aivle.carekids.admin.hospital.service;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.hospital.dto.HospitalDetailDto;
import com.aivle.carekids.domain.hospital.dto.HospitalListDto;
import com.aivle.carekids.domain.hospital.model.Hospital;
import com.aivle.carekids.domain.hospital.model.HospitalOperateTime;
import com.aivle.carekids.domain.hospital.repository.HospitalOperateTImeRespository;
import com.aivle.carekids.domain.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalAdminService {

    private final HospitalRepository hospitalRepository;
    private final HospitalOperateTImeRespository hospitalOperateTImeRespository;
    private final ModelMapper entityModelMapper;

    @Transactional
    public ResponseEntity<?> editHospital(HospitalDetailDto hospitalDetailDto) {

        Region targetRegion = entityModelMapper.map(hospitalDetailDto.getRegion(), Region.class);
        List<HospitalOperateTime> hospitalOperateTimeList = new ArrayList<>();
        if (hospitalDetailDto.getHospitalId() == null) {
            //생성
            Hospital newHospital = Hospital.createNewHospital(hospitalDetailDto);
            newHospital.setRegionInfo(targetRegion);
            hospitalRepository.save(newHospital);

            //운영 시간 등록
            hospitalDetailDto.getHospitalOperateTimes().forEach(operateTime -> {
                HospitalOperateTime newHospitalOperateTime = HospitalOperateTime.createNewHospitalOperateTime(operateTime);
                newHospitalOperateTime.setHospitalInfo(newHospital);
                hospitalOperateTimeList.add(newHospitalOperateTime);
            });

            hospitalOperateTImeRespository.saveAll(hospitalOperateTimeList);

            return ResponseEntity.ok(Map.of("message", "병원 정보가 등록되었습니다."));
        }

        Optional<Hospital> targetHospital = hospitalRepository.findById(hospitalDetailDto.getHospitalId());
        if (targetHospital.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-found", "병원 정보를 찾을 수 없습니다."));
        }

        targetHospital.get().updateHospitalInfo(hospitalDetailDto);
        targetHospital.get().setRegionInfo(targetRegion);
        targetHospital.get().clearOperateTime();

        //운영 시간 재등록
        hospitalDetailDto.getHospitalOperateTimes().forEach(operateTime -> {
            HospitalOperateTime newHospitalOperateTime = HospitalOperateTime.createNewHospitalOperateTime(operateTime);
            newHospitalOperateTime.setHospitalInfo(targetHospital.get());
            hospitalOperateTimeList.add(newHospitalOperateTime);
        });

        hospitalOperateTImeRespository.saveAll(hospitalOperateTimeList);
        return ResponseEntity.ok(Map.of("message", "병원 정보가 수정되었습니다."));
    }


    @Transactional
    public ResponseEntity<?> deleteHospital(Long hospitalId) {
        Optional<Hospital> targetHospital = hospitalRepository.findById(hospitalId);
        if (targetHospital.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-found", "병원 정보를 찾을 수 없습니다."));
        }

        targetHospital.get().deletedHospital(true);
        return ResponseEntity.ok(Map.of("message", "병원 정보가 삭제되었습니다."));
    }

    public PageInfoDto displayHospitalPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HospitalListDto> hospitalPage = hospitalRepository.findAllByOrderByUpdatedAtDescByPageByRegion(null, pageable);

        return new PageInfoDto(new PageInfoDto.PageInfo(
                hospitalPage.getTotalPages(),
                hospitalPage.getNumber() + 1,
                hospitalPage.getSize(),
                hospitalPage.getNumberOfElements()
        ), null, null, hospitalPage.getContent());
    }
}
