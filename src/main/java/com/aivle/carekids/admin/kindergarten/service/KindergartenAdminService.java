package com.aivle.carekids.admin.kindergarten.service;

import com.aivle.carekids.admin.kindergarten.dto.KindergartenAdminDto;
import com.aivle.carekids.domain.kindergarten.model.Kindergarten;
import com.aivle.carekids.domain.kindergarten.repository.KindergartenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KindergartenAdminService {
    private final KindergartenRepository kindergartenRepository;

    @Transactional
    public Map<String, String> editKindergarten(KindergartenAdminDto kindergartenAdminDto) {

        if (kindergartenAdminDto.getKindergartenId() != null){ // -> 수정
            Optional<Kindergarten> targetKindergarten = kindergartenRepository.findById(kindergartenAdminDto.getKindergartenId());
            if (targetKindergarten.isEmpty()) { return Map.of("message", "존재하지 않는 게시글입니다."); }
            targetKindergarten.get().updateKindergarten(kindergartenAdminDto);
            return Map.of("message", "수정이 완료되었습니다.");
        }

        //게시글 작성 후 저장
        Kindergarten newKindergarten = Kindergarten.createKindergarten(kindergartenAdminDto);
        kindergartenRepository.save(newKindergarten);

        return Map.of("message", "주말 어린이집이 생성되었습니다.");
    }
}
