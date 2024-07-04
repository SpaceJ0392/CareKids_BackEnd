package com.aivle.carekids.domain.kidspolicy.dto;

import com.aivle.carekids.domain.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
public class KidsPolicyListDto extends BaseDto {

    @NotEmpty @JsonProperty("id")
    private Long kidsPolicyId;

    @NotEmpty @JsonProperty("title")
    private String kidsPolicyTitle;

    @JsonProperty("description")
    private String kidsPolicyText;

    @QueryProjection
    public KidsPolicyListDto(LocalDateTime createdAt, LocalDateTime updatedAt, Long kidsPolicyId, String kidsPolicyTitle, String kidsPolicyText) {
        super(createdAt, updatedAt);
        this.kidsPolicyId = kidsPolicyId;
        this.kidsPolicyTitle = kidsPolicyTitle;
        this.kidsPolicyText = kidsPolicyText;
    }

}
