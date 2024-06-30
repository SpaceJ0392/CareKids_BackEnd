package com.aivle.carekids.domain.user.general.validation;

import com.aivle.carekids.domain.user.models.SocialType;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SignUpValid {

    private final UsersRepository usersRepository;
    private final Map<String, String> message = new HashMap<>();


    public Map<String, String> emailValidation(String usersEmail) {
        message.clear();

        if (usersEmail.isBlank()){
            message.put("empty-email", "이메일이 존재하지 않습니다.");
        }

        if (usersRepository.existsByUsersEmail(usersEmail)) {
            message.put("exists-email", "이미 사용 중인 계정입니다.");
        }

        if (!usersEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            message.put("error-email-pattern", "이메일의 형식이 유효하지 않습니다.");
        }

        return message;
    }

    public Map<String, String> nickNameValidation(String usersNickname) {
        message.clear();

        if (usersRepository.existsByUsersNickname(usersNickname)) {
            message.put("exists-nickname", "이미 사용 중인 닉네임입니다.");
        }

        return message;
    }

    public Map<String, String> passwordValidation(String password, String usersSocialType){
        message.clear();

        if (!usersSocialType.isBlank() && !password.isBlank()){
            message.put("password", "소셜 로그인은 비밀번호를 받을 수 없습니다.");
            return message;
        }

        if (usersSocialType.isBlank() && password.isBlank()){
            message.put("password", "비밀번호는 10자에서 20자 내외여야 합니다");
            return message;
        }

        if (!usersSocialType.isBlank()){
            return socialTypeValidation(usersSocialType);
        }

        // 일반 회원 가입 확인 - social은 반드시 비어 있어야 하며, password는 무조건 있어야 함.
        if (password.length() <= 9 || password.length() > 20){
            message.put("password", "비밀번호는 10자에서 20자 내외여야 합니다");
        }

        return message;
    }

    public Map<String, String> socialTypeValidation(String socialType){
        message.clear();

        if (Arrays.stream(SocialType.values()).map(Enum::name).toList().contains(socialType)) {
            return message;
        }

        message.put("social-type-error", "존재하지 않는 소셜 로그인 타입입니다");
        return message;
    }
}
