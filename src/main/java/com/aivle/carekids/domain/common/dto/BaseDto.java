package com.aivle.carekids.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
