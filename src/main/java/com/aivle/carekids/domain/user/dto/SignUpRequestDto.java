package com.aivle.carekids.domain.user.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.user.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class SignUpRequestDto {

    @JsonProperty("email")
    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String usersEmail;

    @JsonProperty("password")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 10, max = 25, message = "비밀번호는 최소 {min}자에서 {max}자여야 합니다.")
    private String usersPassword;

    @JsonProperty("nickname")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String usersNickname;

    private Role userStatus = Role.USER; // 기본값 유저

    @JsonProperty("region")
    @Valid
    private RegionDto region;

    @JsonProperty("age-tag")
    @NotEmpty(message = "자녀들의 연령대를 선택해주세요.")
    private List<AgeTagDto> ageTags = new ArrayList<>();
}
