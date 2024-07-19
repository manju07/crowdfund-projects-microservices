package com.crowdfund.projects.microservices.projectservice.mapper;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.crowdfund.projects.microservices.common.code.dto.ProjectReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.ProjectResDTO;
import com.crowdfund.projects.microservices.common.code.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "status", constant = "IN_PROGRESS", resultType = ProjectStatus.class)
    Project projectReqDTOToProject(ProjectReqDTO projectReqDTO);

    ProjectResDTO projectToProjectResDTO(Project project);

    List<ProjectResDTO> convert(List<Project> projects);

}
