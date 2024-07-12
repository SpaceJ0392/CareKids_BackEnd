package com.aivle.carekids.domain.user.general.service;

import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.common.service.CommonService;
import com.aivle.carekids.domain.user.dto.NickNameValidDto;
import com.aivle.carekids.domain.user.dto.PasswordDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.aivle.carekids.domain.user.general.validation.SignUpValid;
import com.aivle.carekids.domain.user.models.Kids;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.KidsRepository;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.Variable.GlobelVar;
import com.aivle.carekids.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
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
    private final SignUpValid signUpValid;

    private final ModelMapper entityModelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CommonService commonService;

    public ResponseEntity<?>  signUp(String email, String socialType) {
        //이메일이 이메일 형식인지, social type에 우리가 가지고 있는 소셜 타입만 들어왔는지 확인
        if (email != null && socialType != null){
            Map<String, String> message = new HashMap<>();
            message.putAll(signUpValid.emailValidation(email));
            message.putAll(signUpValid.socialTypeValidation(socialType));

            if (!message.isEmpty()){ return ResponseEntity.badRequest().body(message); }
        }

        return ResponseEntity.ok(commonService.regionAgeTagAll());
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
        return ResponseEntity.created(new URI( "http://localhost:3000" + "/login")).body(message); // GlobelVar.CLIENT_BASE_URL
    }

    public Users findByUsersId(Long usersId){
        return usersRepository.findByUsersId(usersId)
                .orElseThrow(() -> new UserNotFoundException("미 등록 유저입니다."));
    }

    public UsersDetailDto displayUsersDetail(Long usersId){
        return  usersRepository.findUsersDetailWithRegionAndKids(usersId)
                .orElse(null);
    }

    public ResponseEntity<?> checkNickName(NickNameValidDto nickNameValidDto) {

        Map<String, String> message = new HashMap<>(signUpValid.nickNameValidation(nickNameValidDto.getNickName()));
        if (message.isEmpty()){
            return ResponseEntity.ok(Map.of("message", "중복된 닉네임이 존재하지 않습니다."));
        }

        return ResponseEntity.badRequest().body(message);
    }

    @Transactional
    public ResponseEntity<?> changePassword(HttpHeaders headers, PasswordDto passwordDto) {

        // 유효성 검사
        Map<String, String> message = new HashMap<>(signUpValid.passwordValidation(passwordDto.getNewUsersPassword(), ""));
        if (!message.isEmpty()) {
            return ResponseEntity.badRequest().headers(headers).body(message);
        }

        Optional<Users> users = usersRepository.findByUsersId(passwordDto.getUsersId());
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().headers(headers).body(Map.of("users-not-found", "해당 사용자를 찾을 수 없습니다."));
        }

        users.get().changeUsersPassword(passwordDto.getNewUsersPassword());
        return ResponseEntity.created(URI.create(GlobelVar.CLIENT_BASE_URL)).body(Map.of("message", "비밀번호가 변경되었습니다."));
    }
}
