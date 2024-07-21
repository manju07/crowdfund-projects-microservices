package com.crowdfund.projects.microservices.common.code.entity;

import com.crowdfund.projects.microservices.common.code.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manjunath Asundi
 */
@Entity(name = "wallet")
@Table(name = "wallet")
@Data
@ToString
@NoArgsConstructor
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Float balance;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wallet")
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
    }
}