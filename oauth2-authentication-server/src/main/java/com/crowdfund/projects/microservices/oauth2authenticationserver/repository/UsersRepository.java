package com.crowdfund.projects.microservices.oauth2authenticationserver.repository;

import com.crowdfund.projects.microservices.common.code.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 *
 * @author Manjunath Asundi
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);
}