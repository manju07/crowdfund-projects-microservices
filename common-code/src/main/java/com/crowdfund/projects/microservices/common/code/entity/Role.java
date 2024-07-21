package com.crowdfund.projects.microservices.common.code.entity;

import com.crowdfund.projects.microservices.common.code.constant.UserRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Role entity
 *
 * @author Manjunath Asundi
 */
@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 4634758593179611656L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", updatable = false)
    private UserRole name;

    @JsonBackReference
    @OneToMany(mappedBy = "role")
    private List<User> userList = new ArrayList<User>();

    public void addUser(User user) {
        this.userList.add(user);
    }

    public void removeUser(User user) {
        this.userList.remove(user);
    }
}