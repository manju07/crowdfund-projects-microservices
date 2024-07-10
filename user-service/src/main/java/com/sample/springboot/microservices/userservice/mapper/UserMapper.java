package com.sample.springboot.microservices.userservice.mapper;

import com.sample.springboot.microservices.common.code.dto.UserReqDTO;
import com.sample.springboot.microservices.common.code.dto.UserResDTO;
import com.sample.springboot.microservices.common.code.entity.User;
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
