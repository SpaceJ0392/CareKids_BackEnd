package com.aivle.carekids.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EmailDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;

    private String code;
}
