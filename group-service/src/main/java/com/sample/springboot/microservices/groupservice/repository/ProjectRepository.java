package com.sample.springboot.microservices.groupservice.repository;

import com.sample.springboot.microservices.common.code.entity.Address;
import com.sample.springboot.microservices.common.code.entity.Project;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AddressRepository
 * @author Manjunath Asundi
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}