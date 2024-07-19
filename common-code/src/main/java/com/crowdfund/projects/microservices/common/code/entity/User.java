package com.crowdfund.projects.microservices.common.code.entity;

import com.crowdfund.projects.microservices.common.code.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
@Data
@NoArgsConstructor
public class User extends BaseEntity {

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

    private Boolean isDeleted = false;

    private Boolean isEnabled = false;

    @Column(nullable = false)
    private String gender;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Project> projectSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Contribute> contributes = new HashSet<>();
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

    public void addProject(Project project) {
        projectSet.add(project);
    }

    public void removeProject(Project project) {
        projectSet.remove(project);
    }
}