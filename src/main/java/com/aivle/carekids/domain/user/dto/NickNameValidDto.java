package com.aivle.carekids.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class NickNameValidDto {

    @JsonProperty("is-unique")
    private boolean isUnique;

    @JsonProperty("nickname")
    private String nickName;
}
