package com.crowdfund.projects.microservices.userservice.service.impl;

import com.crowdfund.projects.microservices.common.code.entity.Role;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.userservice.repository.RoleRepository;
import com.crowdfund.projects.microservices.userservice.repository.UserRepository;
import com.crowdfund.projects.microservices.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service implementation
 *
 * @author Manjunath Asundi
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public User addUser(User user, OAuth2Authentication authentication) throws ResourceNotFoundException, CustomException {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent())
                throw new CustomException("conflict", HttpStatus.CONFLICT, "Email already exist");

            if (userRepository.findByPhone(user.getPhone()).isPresent())
                throw new CustomException("conflict", HttpStatus.CONFLICT, "Phone number already exist");

            Role role = roleRepository.findByName(user.getRole().getName());
            if (role == null)
                throw new CustomException("Invalid role", HttpStatus.BAD_REQUEST, "Role doesn't exist");

            user.setRole(role);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setIsEnabled(true);

            Wallet wallet = new Wallet();
            wallet.setBalance(10000f);
            wallet.setUser(user);
            user.setWallet(wallet);

            String loggedInUser = "self";
            if (authentication != null)
                loggedInUser = authentication.getName();
            user.setCreatedBy(loggedInUser);
            user.setUpdatedBy(loggedInUser);

            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (Exception e) {
            log.error("addUser thrown error" + e);
            throw e;
        }
    }
}