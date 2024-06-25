package com.aivle.carekids.domain.common.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends BaseCreatedAt{
    // TODO - 작성일자 혹은 수정일자 순 정렬이 많다면, 인덱스 고려 혹은 캐시 고려

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
