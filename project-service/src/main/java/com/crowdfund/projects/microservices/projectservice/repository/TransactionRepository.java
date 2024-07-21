package com.crowdfund.projects.microservices.projectservice.repository;

import com.crowdfund.projects.microservices.common.code.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AddressRepository
 *
 * @author Manjunath Asundi
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);

    Page<Transaction> findAll(Pageable pageable);

}