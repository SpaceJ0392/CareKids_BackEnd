package com.aivle.carekids.admin.kindergarten.service;

import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.hospital.model.Hospital;
import com.aivle.carekids.domain.hospital.model.HospitalOperateTime;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.model.Kindergarten;
import com.aivle.carekids.domain.kindergarten.model.KindergartenOperateTime;
import com.aivle.carekids.domain.kindergarten.repository.KindergartenOperateTimeRepository;
import com.aivle.carekids.domain.kindergarten.repository.KindergartenRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
public class KindergartenAdminService {
    private final KindergartenRepository kindergartenRepository;
    private final KindergartenOperateTimeRepository kindergartenOperateTimeRepository;
    private final ModelMapper entityModelMapper;

    @Transactional
    public ResponseEntity<?> editKindergarten(KindergartenDetailDto kindergartenDetailDto) {

        Region targetRegion = entityModelMapper.map(kindergartenDetailDto.getRegion(), Region.class);
        List<KindergartenOperateTime> kindergartenOperateTimeList = new ArrayList<>();


        if (kindergartenDetailDto.getKindergartenId() == null) { // -> 생성

            Kindergarten newKindergarten = Kindergarten.createNewKindergarten(kindergartenDetailDto);
            newKindergarten.setRegionInfo(targetRegion);
            kindergartenRepository.save(newKindergarten);

            //운영 시간 등록
            kindergartenDetailDto.getKindergartenOperateTimes().forEach(operateTime -> {
                KindergartenOperateTime newKindergartenOperateTime = KindergartenOperateTime.createNewKindergartenOperateTime(operateTime);
                newKindergartenOperateTime.setKindergartenInfo(newKindergarten);
                kindergartenOperateTimeList.add(newKindergartenOperateTime);
            });

            kindergartenOperateTimeRepository.saveAll(kindergartenOperateTimeList);

            return ResponseEntity.ok(Map.of("message", "어린이집 정보가 등록되었습니다."));
        }

        Optional<Kindergarten> targetKindergarten = kindergartenRepository.findById(kindergartenDetailDto.getKindergartenId());
        if (targetKindergarten.isEmpty()) { return ResponseEntity.badRequest().body(Map.of("not-found", "어린이집 정보를 찾을 수 없습니다.")); }

        targetKindergarten.get().updateKindergartenInfo(kindergartenDetailDto);
        targetKindergarten.get().setRegionInfo(targetRegion);
        targetKindergarten.get().clearOperateTime();

        //운영 시간 재등록
        kindergartenDetailDto.getKindergartenOperateTimes().forEach(operateTime -> {
            KindergartenOperateTime newKindergartenOperateTime = KindergartenOperateTime.createNewKindergartenOperateTime(operateTime);
            newKindergartenOperateTime.setKindergartenInfo(targetKindergarten.get());
            kindergartenOperateTimeList.add(newKindergartenOperateTime);
        });

        kindergartenOperateTimeRepository.saveAll(kindergartenOperateTimeList);
        return ResponseEntity.ok(Map.of("message", "어린이집 정보가 수정되었습니다."));
    }

    @Transactional
    public ResponseEntity<?> deleteKindergarten(Long kindergartenId) {
        Optional<Kindergarten> targetKindergarten = kindergartenRepository.findById(kindergartenId);
        if (targetKindergarten.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-found", "어린이집 정보를 찾을 수 없습니다."));
        }

        targetKindergarten.get().deletedKindergarten(true);
        return ResponseEntity.ok(Map.of("message", "어린이집 정보가 삭제되었습니다."));
    }
}
