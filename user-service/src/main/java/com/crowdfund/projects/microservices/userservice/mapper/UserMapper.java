package com.crowdfund.projects.microservices.userservice.mapper;

import com.crowdfund.projects.microservices.common.code.dto.UserReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.UserResDTO;
import com.crowdfund.projects.microservices.common.code.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role", target = "role.name")
    User userAddDTOToUser(UserReqDTO userAddDTO);

    UserResDTO userToUserResponse(User user);
}
