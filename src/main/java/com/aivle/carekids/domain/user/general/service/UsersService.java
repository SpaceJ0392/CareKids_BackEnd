package com.aivle.carekids.domain.user.general.service;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.common.repository.AgeTagRepository;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import com.aivle.carekids.domain.user.dto.SignUpDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.general.validation.SignUpValid;
import com.aivle.carekids.domain.user.models.Kids;
import com.aivle.carekids.domain.user.models.Role;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.KidsRepository;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;
    private final KidsRepository kidsRepository;
    private final RegionRepository regionRepository;
    private final AgeTagRepository ageTagRepository;
    private final SignUpValid signUpValid;

    private final ObjectMapper objectMapper;
    private final ModelMapper dtoModelMapper;
    private final ModelMapper entityModelMapper;


    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Object>  signUp(String email, String socialType) {
        //이메일이 이메일 형식인지, social type에 우리가 가지고 있는 소셜 타입만 들어왔는지 확인
        if (email != null && socialType != null){
            Map<String, String> message = new HashMap<>();
            message.putAll(signUpValid.emailValidation(email));
            message.putAll(signUpValid.socialTypeValidation(socialType));

            if (!message.isEmpty()){ return ResponseEntity.badRequest().body(message); }
        }

        List<RegionDto> regions = regionRepository.findAll().stream()
                .map(r -> dtoModelMapper.map(r, RegionDto.class)).toList();

        List<AgeTagDto> ageTags = ageTagRepository.findAll().stream()
                .map(a -> dtoModelMapper.map(a, AgeTagDto.class)).toList();

        return ResponseEntity.ok(new SignUpDto(ageTags,regions));
    }


    @Transactional
    public ResponseEntity<Map<String, String>> signUpRequest(SignUpRequestDto signUpData) throws URISyntaxException {
        if (signUpData == null) { return ResponseEntity.badRequest().body(new HashMap<>()); }

        // 유효성 검사 (이메일 & 닉네임 & 비밀번호)
        Map<String, String> message= new HashMap<>();
        message.putAll(signUpValid.emailValidation(signUpData.getUsersEmail()));
        message.putAll(signUpValid.nickNameValidation(signUpData.getUsersNickname()));
        message.putAll(signUpValid.passwordValidation(signUpData.getUsersPassword(), signUpData.getUsersSocialType()));
        if (!message.isEmpty()){
            return ResponseEntity.badRequest().body(message);
        }

        // Users Entity에 저장
        if (signUpData.getUsersSocialType().isBlank()) {
            signUpData.setUsersPassword(passwordEncoder.encode(signUpData.getUsersPassword()));
        }
        Users newUsers = Users.createNewUser(signUpData);

        Region region = entityModelMapper.map(signUpData.getRegion(), Region.class);
        newUsers.setRegionInfo(region);
        usersRepository.save(newUsers);

        // Kids Entity에 저장
        List<Kids> newKids = new ArrayList<>();

        signUpData.getAgeTags().forEach(ageTagDto -> {
            AgeTag ageTag = entityModelMapper.map(ageTagDto, AgeTag.class);
            newKids.add(Kids.setKidsInfo(newUsers, ageTag));
        });

        kidsRepository.saveAll(newKids);

        message.put("message", "회원 가입이 완료되었습니다.");
        return ResponseEntity.created(new URI("http://localhost:8080/api/signin")).body(message);
    }


}
