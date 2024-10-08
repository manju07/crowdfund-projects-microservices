package com.crowdfund.projects.microservices.userservice.service;

import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author Manjunath Asundi
 */
@Service
public interface UserService {
    User addUser(User user, OAuth2Authentication authentication) throws CustomException, ResourceNotFoundException;
}