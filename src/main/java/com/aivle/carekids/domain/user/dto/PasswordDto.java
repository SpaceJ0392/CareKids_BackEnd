package com.aivle.carekids.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PasswordDto {

    @JsonProperty("user-id")
    private Long usersId;

    @JsonProperty("new-password")
    private String newUsersPassword;
}
