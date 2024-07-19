package com.crowdfund.projects.microservices.projectservice.dao.impl;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.entity.Contribute;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.dao.ContributeDAO;
import com.crowdfund.projects.microservices.projectservice.repository.ContributeRepository;
import com.crowdfund.projects.microservices.projectservice.repository.ProjectRepository;
import com.crowdfund.projects.microservices.projectservice.repository.UserRepository;
import com.crowdfund.projects.microservices.projectservice.util.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
@Slf4j
public class ContributeDAOImpl implements ContributeDAO {

    @Autowired
    private ContributeRepository contributeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

//    @PersistenceContext
//    private EntityManager entityManager;

    @Override
//    @Transactional
    public Contribute saveContribute(Contribute contribute, Long projectId) throws CustomException, ResourceNotFoundException {
        try {
            String userName = UserData.getUserName();
            Optional<User> userOptional = userRepository.findByUserName(userName);
//
            if (!userOptional.isPresent())
                throw new CustomException("Invalid User", HttpStatus.NOT_FOUND, "User does not exist");
            User user = userOptional.get();
            contribute.setUser(user);

            Optional<Project> projectOptional = projectRepository.findById(projectId);

            if (!projectOptional.isPresent())
                throw new ResourceNotFoundException("Project does not exist with id = " + projectId);

            Project project = projectOptional.get();

            if (!ProjectStatus.IN_PROGRESS.equals(project.getStatus()))
                throw new CustomException("Invalid Project status", HttpStatus.BAD_REQUEST, "Project not in valid state");

            project.setReceivedAmount(project.getReceivedAmount() + contribute.getAmount());

            if (project.getReceivedAmount() >= project.getRequiredAmount() ) {
                project.setStatus(ProjectStatus.COMPLETED);
            }

            project.addContribute(contribute);
            contribute.setProject(project);
//            user.addContribute(contribute);

            contribute.setCreatedBy(userName);
            contribute.setUpdatedBy(userName);
//            entityManager.persist(contribute);
//            entityManager.flush();
//            entityManager.clear();
            return contributeRepository.save(contribute);
        } catch (Exception e) {
            log.error("ContributeDAOImpl exception", e);
            throw e;
        }
    }
}
