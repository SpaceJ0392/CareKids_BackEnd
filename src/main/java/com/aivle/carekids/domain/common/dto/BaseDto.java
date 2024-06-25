package com.aivle.carekids.domain.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BaseDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
