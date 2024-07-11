package com.crowdfund.projects.microservices.projectservice.repository;

import com.crowdfund.projects.microservices.common.code.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AddressRepository
 * @author Manjunath Asundi
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}