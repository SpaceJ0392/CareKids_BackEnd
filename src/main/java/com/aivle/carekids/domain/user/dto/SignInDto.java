package com.aivle.carekids.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SignInDto {
    private String usersEmail;
    private String usersPassword;
}
