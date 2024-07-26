package com.crowdfund.projects.microservices.oauth2authenticationserver.entity;

import com.crowdfund.projects.microservices.common.code.constant.UserRole;
import com.crowdfund.projects.microservices.common.code.entity.Role;
import com.crowdfund.projects.microservices.common.code.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsTest {

    private static String USERNAME = "manjunath@gmail.com";
    private CustomUserDetails customUserDetails = getCustomUserDetails();

    @Test
    void getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
    }

    @Test
    void getPassword() {
        String password = customUserDetails.getPassword();
        assertEquals("Test@123", password);
    }

    @Test
    void getUsername() {
        String username = customUserDetails.getUsername();
        assertEquals(USERNAME, username);
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(customUserDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(customUserDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(customUserDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(customUserDetails.isEnabled());
    }

    private CustomUserDetails getCustomUserDetails() {
        User user = new User();
        user.setId(1L);
        user.setUserName(USERNAME);
        user.setEmail(USERNAME);
        user.setIsEnabled(true);
        user.setGender("MALE");
        user.setFName("Manjunath");
        user.setLName("Asundi");
        user.setPhone("9886988915");
        user.setPassword("Test@123");
        Role role = new Role();
        role.setName(UserRole.DONOR);
        user.setRole(role);
        return new CustomUserDetails(user);
    }
}