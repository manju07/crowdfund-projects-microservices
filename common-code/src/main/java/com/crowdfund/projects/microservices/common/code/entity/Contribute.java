package com.crowdfund.projects.microservices.common.code.entity;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.crowdfund.projects.microservices.common.code.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Manjunath Asundi
 */
@Entity(name = "contribute")
@Table(name = "contribute")
@Data
public class Contribute extends BaseEntity {

    @Id
    @GeneratedValue(generator = "contribute_seq_gen")
    @SequenceGenerator(name = "contribute_seq_gen", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 4098)
    private String description;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contribute_user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
}