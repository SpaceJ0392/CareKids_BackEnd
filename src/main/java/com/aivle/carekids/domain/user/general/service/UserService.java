package com.aivle.carekids.domain.user.general.service;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.common.repository.AgeTagRepository;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import com.aivle.carekids.domain.user.dto.SignUpDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.general.validation.SignUpRequestValid;
import com.aivle.carekids.domain.user.models.Kids;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.KidsRepository;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UsersRepository usersRepository;
    private final KidsRepository kidsRepository;
    private final RegionRepository regionRepository;
    private final AgeTagRepository ageTagRepository;
    private final SignUpRequestValid requestValid;

    private final ObjectMapper objectMapper;
    private final ModelMapper dtoModelMapper;
    private final ModelMapper entityModelMapper;


    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<SignUpDto>  signUp() {
        List<RegionDto> regions = regionRepository.findAll().stream()
                .map(r -> dtoModelMapper.map(r, RegionDto.class)).toList();

        List<AgeTagDto> ageTags = ageTagRepository.findAll().stream()
                .map(a -> dtoModelMapper.map(a, AgeTagDto.class)).toList();

        return ResponseEntity.ok(new SignUpDto(ageTags,regions));
    }


    @Transactional
    public ResponseEntity<Map<String, String>> signUpRequest(SignUpRequestDto signUpData) throws URISyntaxException {
        if (signUpData == null) { return ResponseEntity.badRequest().body(new HashMap<>()); }

        Map<String, String> message = new HashMap<>();
        if (requestValid.EmailValidation(signUpData.getUsersEmail())) { // 중복 검사 (이메일)
            message.put("message", "이미 사용 중인 계정입니다.");
            return ResponseEntity.badRequest().body(message);
        }

        if (requestValid.NickNameValidation(signUpData.getUsersNickname())){
            message.put("message", "이미 사용 중인 닉네임입니다.");
            return ResponseEntity.badRequest().body(message);
        }

        // Users Entity에 저장
        signUpData.setUsersPassword(passwordEncoder.encode(signUpData.getUsersPassword()));
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
        return ResponseEntity.created(new URI("http://localhost:8080/signin")).body(message);
    }

    public ResponseEntity<Map<String, String>> signIn() {
        Map<String, String> message = new HashMap<>();
        // TODO - 로그인 with Spring Security

        message.put("message", "로그인이 되었습니다");
        return ResponseEntity.ok(message);
    }
}
