package com.aivle.carekids.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersLightDto {

    @JsonProperty("id")
    private Long usersId;

    @JsonProperty("nickname")
    private String usersNickname;
}
