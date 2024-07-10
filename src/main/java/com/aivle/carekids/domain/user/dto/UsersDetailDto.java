package com.aivle.carekids.domain.user.dto;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
public class UsersDetailDto {

    private Long usersId;

    private String usersEmail;

    private String usersNickname;

    private RegionDto usersRegion;

    private List<AgeTagDto> usersAgeTagDtos;

    public UsersDetailDto(Long usersId, String usersEmail, String usersNickname, RegionDto usersRegion) {
        this.usersId = usersId;
        this.usersEmail = usersEmail;
        this.usersNickname = usersNickname;
        this.usersRegion = usersRegion;
    }
}
