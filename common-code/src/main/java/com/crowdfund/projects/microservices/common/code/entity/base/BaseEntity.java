package com.crowdfund.projects.microservices.common.code.entity.base;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @CreatedBy
    @Column(nullable = true, updatable = false)
    private String createdBy;

    @Column(nullable = true)
    @LastModifiedBy
    private String updatedBy;
}
