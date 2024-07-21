package com.crowdfund.projects.microservices.projectservice.repository;

import com.crowdfund.projects.microservices.common.code.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AddressRepository
 *
 * @author Manjunath Asundi
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long id);

    Optional<Wallet> findById(Long id);
}