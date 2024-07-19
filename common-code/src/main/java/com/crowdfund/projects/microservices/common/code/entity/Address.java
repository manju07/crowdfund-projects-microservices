package com.crowdfund.projects.microservices.common.code.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Address Entity
 *
 * @author Manjunath Asundi
 */
@Entity
@Table(name = "address")
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = -6873361542528746767L;

    @Id
    @GeneratedValue(generator = "address_seq_gen")
    @SequenceGenerator(name = "address_seq_gen", initialValue = 2, allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String area;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String pincode;

    @Column(nullable = false, length = 45)
    private String state;

    @Column(nullable = false, length = 45)
    private String country;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @JsonBackReference
    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private User user;
}