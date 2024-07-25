package com.aivle.carekids.admin.playInfo.service;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.playInfo.dto.PlayInfoDetailDto;
import com.aivle.carekids.domain.playInfo.model.DevDomain;
import com.aivle.carekids.domain.playInfo.model.PlayInfo;
import com.aivle.carekids.domain.playInfo.model.PlayInfoDomain;
import com.aivle.carekids.domain.playInfo.repository.PlayInfoDomainRepository;
import com.aivle.carekids.domain.playInfo.repository.PlayInfoRepository;
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
public class PlayInfoAdminService {

    private final PlayInfoRepository playInfoRepository;
    private final PlayInfoDomainRepository playInfoDomainRepository;
    private final UsersRepository usersRepository;

    private final ModelMapper entityModelMapper;

    @Transactional
    public ResponseEntity<?> editPlayInfo(PlayInfoDetailDto playInfoDetailDto, Long usersId) {

        Optional<Users> users = usersRepository.findByUsersId(usersId);
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        AgeTag targetAgeTag = entityModelMapper.map(playInfoDetailDto.getAgeTag(), AgeTag.class);
        List<PlayInfoDomain> playInfoDomainList = new ArrayList<>();


        if (playInfoDetailDto.getPlayInfoId() == null) {

            PlayInfo newPlayInfo = PlayInfo.createNewPlayInfo(playInfoDetailDto);
            newPlayInfo.setAgeTagInfo(targetAgeTag);
            newPlayInfo.setUserInfo(users.get());
            playInfoRepository.save(newPlayInfo);

            //발달 영역 등록
            playInfoDetailDto.getDevDomains().forEach(devdomain -> {
                DevDomain targetDevDomain = entityModelMapper.map(devdomain, DevDomain.class);
                PlayInfoDomain newPlayInfoDomain = PlayInfoDomain.createNewPlayInfoDomain(newPlayInfo, targetDevDomain);
                newPlayInfoDomain.setPlayInfoDomainInfo(newPlayInfo, targetDevDomain);
                playInfoDomainList.add(newPlayInfoDomain);
            });

            playInfoDomainRepository.saveAll(playInfoDomainList);

            return ResponseEntity.ok(Map.of("message", "놀이 정보가 등록되었습니다."));
        }

        Optional<PlayInfo> targetPlayInfo = playInfoRepository.findById(playInfoDetailDto.getPlayInfoId());
        if (targetPlayInfo.isEmpty()) { return ResponseEntity.badRequest().body(Map.of("not-found", "놀이 정보를 찾을 수 없습니다.")); }

        targetPlayInfo.get().updatePlayInfo(playInfoDetailDto);
        targetPlayInfo.get().setAgeTagInfo(targetAgeTag);
        targetPlayInfo.get().setUserInfo(users.get());
        targetPlayInfo.get().clearDevDomains();

        //발달 영역 재등록
        playInfoDetailDto.getDevDomains().forEach(devdomain -> {
            DevDomain targetDevDomain = entityModelMapper.map(devdomain, DevDomain.class);
            PlayInfoDomain newPlayInfoDomain = PlayInfoDomain.createNewPlayInfoDomain(targetPlayInfo.get(), targetDevDomain);
            newPlayInfoDomain.setPlayInfoDomainInfo(targetPlayInfo.get(), targetDevDomain);
            playInfoDomainList.add(newPlayInfoDomain);
        });

        playInfoDomainRepository.saveAll(playInfoDomainList);
        return ResponseEntity.ok(Map.of("message", "놀이 정보가 수정되었습니다."));
    }


    @Transactional
    public ResponseEntity<?> deletePlayInfo(Long playInfoId) {
        Optional<PlayInfo> targetPlayInfo = playInfoRepository.findById(playInfoId);
        if (targetPlayInfo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-found", "놀이 정보를 찾을 수 없습니다."));
        }

        targetPlayInfo.get().deletedPlayInfo(true);
        return ResponseEntity.ok(Map.of("message", "놀이 정보가 삭제되었습니다."));
    }
}
