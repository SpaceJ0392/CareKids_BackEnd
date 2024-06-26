package com.aivle.carekids.domain.user.general.validation;

import com.aivle.carekids.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestValid {

    private final UserRepository userRepository;

    public boolean EmailValidation(String usersEmail){
        return userRepository.existsByUserEmail(usersEmail);
    }

    public boolean NickNameValidation(String usersNickname) {
        return userRepository.existsByUserNickname(usersNickname);
    }
}
