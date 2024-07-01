package com.aivle.carekids.domain.user.general.validation;

import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestValid {

    private final UsersRepository usersRepository;

    public boolean EmailValidation(String usersEmail){
        return usersRepository.existsByUsersEmail(usersEmail);
    }

    public boolean NickNameValidation(String usersNickname) {
        return usersRepository.existsByUsersNickname(usersNickname);
    }
}
