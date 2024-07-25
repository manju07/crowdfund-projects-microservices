package com.crowdfund.projects.microservices.projectservice;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ProjectServiceTestUtils {

    public static final String USERNAME = "manjunath@gmail.com";

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName(USERNAME);
        user.setEmail(USERNAME);
        user.setIsEnabled(true);
        user.setGender("MALE");
        user.setFName("Manjunath");
        user.setLName("Asundi");
        user.setPhone("9886988915");
        return user;
    }

    public static Project getProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project-1");
        project.setDescription("AI based analytics model");
        project.setRequiredAmount(1000);
        project.setReceivedAmount(0);
        project.setStatus(ProjectStatus.IN_PROGRESS);
        return project;
    }

    public static Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(1000);
        transaction.setProject(getProject());
        return transaction;
    }

    public static Wallet getWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(1000F);
        wallet.setUser(getUser());
        return wallet;
    }

    public static Authentication getUserAuthentication() {
        return new Authentication() {
            @Override
            public boolean equals(Object another) {
                return false;
            }

            @Override
            public String toString() {
                return null;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String getName() {
                return USERNAME;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return USERNAME;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }
        };
    }
}
