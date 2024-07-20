package com.crowdfund.projects.microservices.userservice.service.impl;

import com.crowdfund.projects.microservices.common.code.entity.Role;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.constant.UserRole;
import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.userservice.repository.UserRepository;
import com.crowdfund.projects.microservices.userservice.util.UserData;
import com.crowdfund.projects.microservices.userservice.repository.RoleRepository;
import com.crowdfund.projects.microservices.userservice.service.UserService;

// import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
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
    public User addUser(User user) throws ResourceNotFoundException, CustomException {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent())
                throw new CustomException("conflict", HttpStatus.CONFLICT, "Email already exist");

            if (userRepository.findByPhone(user.getPhone()).isPresent())
                throw new CustomException("conflict", HttpStatus.CONFLICT, "Phone number already exist");

            Role role = roleRepository.findByName(user.getRole().getName());
            if (role == null)
                throw new CustomException("Invalid role", HttpStatus.BAD_REQUEST, "Role doesn't exist");

            user.setRole(role);
            String userName = UserData.getUserName();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setIsEnabled(true);
            user.setCreatedBy(userName);
            user.setUpdatedBy(userName);

            Wallet wallet = new Wallet();
            wallet.setBalance(10000f);
            wallet.setUser(user);
            user.setWallet(wallet);

            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (Exception e) {
            log.error("addUser thrown error" + e);
            throw e;
        }
    }

    @Override
    public User updateUser(Long userId, User user) throws ResourceNotFoundException, CustomException {
        try {
            String userName = UserData.getUserName();
            User userData = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with id:" + userId));
            userData.setFName(user.getFName());
            userData.setLName(user.getLName());
            userData.setGender(user.getGender());


            UserRole userRole = user.getRole().getName();
            if (!userRole.toString().equals("MANAGER") && !userRole.toString().equals("PARTNER"))
                throw new CustomException("You can update account for Manager/Partner only...!");
            else {
                Role role = roleRepository.findByName(userRole);
                role.addUser(userData);
                userData.setRole(role);
            }
            userData.setUpdatedBy(userName);
            return userRepository.save(userData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}