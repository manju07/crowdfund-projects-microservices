package com.sample.springboot.microservices.common.code.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User Entity
 *
 * @author Manjunath Asundi
 */
@Entity(name = "user")
@Table(name = "user")
@NamedQueries(value = {
        @NamedQuery(name = "get_data_by_role", query = "select u from user u join u.role r where r.name=:role and u.createdBy=:createdBy")})
@SQLDelete(sql = "update user set is_deleted=true where id =?")
@Where(clause = "is_deleted=false")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 4163077184739255229L;

    @Id
    @GeneratedValue(generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", initialValue = 2, allocationSize = 1)
    private long id;

    @Column(name = "fname", nullable = false, length = 45)
    private String fName;

    @Column(name = "lname", nullable = false, length = 45)
    private String lName;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Column(nullable = false, unique = true, length = 45)
    private String userName;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    private Boolean isDeleted = false;

    private Boolean isEnabled = false;

    @Column(updatable = false)
    private String createdBy;

    private String updatedBy;

    @Column(nullable = false)
    private String gender;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "innovator_project", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projectSet = new HashSet<Project>();

    public void addProject(Project project) {
        projectSet.add(project);
    }

    public void removeProject(Project project) {
        projectSet.remove(project);
    }

//    @JsonBackReference
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "corporate_employee", joinColumns = {
//            @JoinColumn(name = "employee_id", referencedColumnName = "id") }, inverseJoinColumns = {
//                    @JoinColumn(name = "corporate_id", referencedColumnName = "id") })
//    private Corporate corporate;

//    @JsonBackReference
//    @OneToMany(mappedBy = "engagementManager")
//    private Set<Engagement> engagementList = new HashSet<Engagement>();

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Role role;

    public User(String fName, String lName, String phone, String userName, String email, String password) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

//    public void addEngagement(Engagement engagement) {
//        this.engagementList.add(engagement);
//    }
//
//    public void removeEngagement(Engagement engagement) {
//        this.engagementList.remove(engagement);
//    }

    public User(String fName, String lName, String phone, String userName, String email, String password, Role role) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(User user) {
        this.fName = user.fName;
        this.lName = user.lName;
        this.phone = user.phone;
        this.userName = user.userName;
        this.email = user.email;
        this.password = user.password;
        this.role = user.role;
    }
}