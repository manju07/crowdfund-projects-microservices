package com.crowdfund.projects.microservices.common.code.entity;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manjunath Asundi
 */
@Entity(name = "project")
@Table(name = "project")
@SQLDelete(sql = "update project set is_deleted = true where id =?")
@Where(clause = "is_deleted=false")
@Data
@NoArgsConstructor
@ToString
public class Project extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 4098)
    private String description;

    @Column(nullable = false)
    private float requiredAmount;

    @Column(nullable = false)
    private float receivedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Contribute> contributes = new ArrayList<>();

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Transaction> transactionsList = new ArrayList<>();

    public void addContribute(Contribute contribute) {
        this.contributes.add(contribute);
    }

    public void removeContribute(Contribute contribute) {
        this.contributes.remove(contribute);
    }

    public void addTransaction(Transaction transaction) {
        this.transactionsList.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        this.transactionsList.remove(transaction);
    }

    @Column(columnDefinition = "tinyint default 0")
    private Boolean isDeleted = false;
}