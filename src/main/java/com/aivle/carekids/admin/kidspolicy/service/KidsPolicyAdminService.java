package com.aivle.carekids.admin.kidspolicy.service;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.kidspolicy.dto.KidsPolicyDetailDto;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicy;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyAgeTag;
import com.aivle.carekids.domain.kidspolicy.models.KidsPolicyRegion;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyAgeTagRepository;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyRegionRepository;
import com.aivle.carekids.domain.kidspolicy.repository.KidsPolicyRepository;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.UsersRepository;
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
public class KidsPolicyAdminService {

    private final KidsPolicyRepository kidsPolicyRepository;
    private final KidsPolicyRegionRepository kidsPolicyRegionRepository;
    private final KidsPolicyAgeTagRepository kidsPolicyAgeTagRepository;
    private final UsersRepository usersRepository;

    private final ModelMapper entityModelMapper;

    @Transactional
    public ResponseEntity<?> editKidsPolicy(KidsPolicyDetailDto kidsPolicyDetailDto, Long usersId) {

        Optional<Users> users = usersRepository.findByUsersId(usersId);
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        if (kidsPolicyDetailDto.getKidsPolicyId() == null) {
            //생성
            KidsPolicy newKidsPolicy = KidsPolicy.createNewKidsPolicy(kidsPolicyDetailDto);
            newKidsPolicy.setUserInfo(users.get());
            kidsPolicyRepository.save(newKidsPolicy);

            //지역 추가
            List<KidsPolicyRegion> newKidsPolicyRegionList = new ArrayList<>();
            kidsPolicyDetailDto.getRegionDtos().forEach(region -> {
                Region targetRegion = entityModelMapper.map(region, Region.class);
                KidsPolicyRegion newKidsPolicyRegion = KidsPolicyRegion.createNewKidsPolicyRegion(newKidsPolicy, targetRegion);
                newKidsPolicyRegion.setKidsPolicyRegionInfo(newKidsPolicy, targetRegion);
                newKidsPolicyRegionList.add(newKidsPolicyRegion);
            });

            kidsPolicyRegionRepository.saveAll(newKidsPolicyRegionList);

            //연령대 추가
            List<KidsPolicyAgeTag> newKidsPolicyAgeTagList = new ArrayList<>();
            kidsPolicyDetailDto.getAgeTagDtos().forEach(ageTag -> {
                AgeTag targetAgeTag = entityModelMapper.map(ageTag, AgeTag.class);
                KidsPolicyAgeTag newKidsPolicyAgeTag = KidsPolicyAgeTag.createNewKidsPolicyAgeTag(newKidsPolicy, targetAgeTag);
                newKidsPolicyAgeTag.setKidsPolicyAgeTagInfo(newKidsPolicy, targetAgeTag);
                newKidsPolicyAgeTagList.add(newKidsPolicyAgeTag);
            });

            kidsPolicyAgeTagRepository.saveAll(newKidsPolicyAgeTagList);

            return ResponseEntity.ok(Map.of("message", "병원 정보가 등록되었습니다."));
        }

        Optional<KidsPolicy> targetKidsPolicy = kidsPolicyRepository.findById(kidsPolicyDetailDto.getKidsPolicyId());
        if (targetKidsPolicy.isEmpty()) { return ResponseEntity.badRequest().body(Map.of("not-found", "해당 육아정책 정보를 찾을 수 없습니다.")); }

        targetKidsPolicy.get().updateKidsPolicyInfo(kidsPolicyDetailDto);
        targetKidsPolicy.get().setUserInfo(users.get());
        targetKidsPolicy.get().clearRegionAgeTag();
        //지역 추가
        List<KidsPolicyRegion> kidsPolicyRegionList = new ArrayList<>();
        kidsPolicyDetailDto.getRegionDtos().forEach(region -> {
            Region targetRegion = entityModelMapper.map(region, Region.class);
            KidsPolicyRegion newKidsPolicyRegion = KidsPolicyRegion.createNewKidsPolicyRegion(targetKidsPolicy.get(), targetRegion);
            newKidsPolicyRegion.setKidsPolicyRegionInfo(targetKidsPolicy.get(), targetRegion);
            kidsPolicyRegionList.add(newKidsPolicyRegion);
        });

        kidsPolicyRegionRepository.saveAll(kidsPolicyRegionList);

        //연령대 추가
        List<KidsPolicyAgeTag> kidsPolicyAgeTagList = new ArrayList<>();
        kidsPolicyDetailDto.getAgeTagDtos().forEach(ageTag -> {
            AgeTag targetAgeTag = entityModelMapper.map(ageTag, AgeTag.class);
            KidsPolicyAgeTag newKidsPolicyAgeTag = KidsPolicyAgeTag.createNewKidsPolicyAgeTag(targetKidsPolicy.get(), targetAgeTag);
            newKidsPolicyAgeTag.setKidsPolicyAgeTagInfo(targetKidsPolicy.get(), targetAgeTag);
            kidsPolicyAgeTagList.add(newKidsPolicyAgeTag);
        });

        kidsPolicyAgeTagRepository.saveAll(kidsPolicyAgeTagList);

        return ResponseEntity.ok(Map.of("message", "육아 정책 정보가 수정되었습니다."));
    }


    @Transactional
    public ResponseEntity<?> deleteKidsPolicy(Long kidsPolicyId) {
        Optional<KidsPolicy> targetKidsPolicy = kidsPolicyRepository.findById(kidsPolicyId);
        if (targetKidsPolicy.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-found", "병원 정보를 찾을 수 없습니다."));
        }

        targetKidsPolicy.get().deletedKidsPolicy(true);
        return ResponseEntity.ok(Map.of("message", "병원 정보가 삭제되었습니다."));
    }
}
