package com.crowdfund.projects.microservices.common.code.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 *
 * @author Manjunath Asundi
 */
@Entity(name = "project")
@Table(name = "project")
@SQLDelete(sql = "update project set is_deleted = true where id =?")
@Where(clause = "is_deleted=false")
@Data
public class Project implements Serializable {

    @Id
    @GeneratedValue(generator = "project_seq_gen")
    @SequenceGenerator(name = "project_seq_gen", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 4098)
    private String description;

    @Column(nullable = false)
    private float required_amount;

    @Column(nullable = false)
    private float received_amount;

    @Column(nullable = false)
    private ProjectStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_time;

    @UpdateTimestamp
    private LocalDateTime updated_time;

    @CreatedBy
    @Column(nullable = true, updatable = false)
    private String createdBy;

    @Column(nullable = true)
    @LastModifiedBy
    private String updatedBy;

    @Column(columnDefinition = "tinyint default 0" )
    private Boolean isDeleted = false;
   }