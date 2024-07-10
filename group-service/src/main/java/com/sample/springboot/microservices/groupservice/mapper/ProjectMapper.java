package com.sample.springboot.microservices.groupservice.mapper;

import com.sample.springboot.microservices.common.code.constant.ProjectStatus;
import com.sample.springboot.microservices.common.code.dto.ProjectReqDTO;
import com.sample.springboot.microservices.common.code.dto.ProjectResDTO;
import com.sample.springboot.microservices.common.code.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "status", constant = "INITIATED", resultType = ProjectStatus.class)
    Project projectReqDTOToProject(ProjectReqDTO projectReqDTO);

    ProjectResDTO projectToProjectResDTO(Project project);
}
