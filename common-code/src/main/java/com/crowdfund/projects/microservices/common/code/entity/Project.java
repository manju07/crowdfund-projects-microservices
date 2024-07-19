package com.crowdfund.projects.microservices.common.code.entity;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.base.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Manjunath Asundi
 */
@Entity(name = "project")
@Table(name = "project")
@SQLDelete(sql = "update project set is_deleted = true where id =?")
@Where(clause = "is_deleted=false")
@Data
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(generator = "project_seq_gen")
    @SequenceGenerator(name = "project_seq_gen", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 4098)
    private String description;

    @Column(nullable = false)
    private float required_amount;

    @Column(nullable = false)
    private float received_amount;

    @Column(nullable = false)
    private ProjectStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "project")
    private Set<Contribute> contributes;

    @Column(columnDefinition = "tinyint default 0")
    private Boolean isDeleted = false;
}