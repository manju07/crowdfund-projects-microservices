package com.crowdfund.projects.microservices.userservice.controller;

import com.crowdfund.projects.microservices.common.code.dto.UserReqDTO;
import com.crowdfund.projects.microservices.common.code.dto.UserResDTO;
import com.crowdfund.projects.microservices.common.code.entity.User;
import com.crowdfund.projects.microservices.common.code.constant.UserRole;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.userservice.mapper.UserMapper;
import com.crowdfund.projects.microservices.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * User management API's
 *
 * @author Manjunath Asundi
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1/user-service/user", produces = "application/json")
@Api(value = "User-App", description = "User operations")
@Validated
@Slf4j
public class UserController {

    private static final UserMapper MAPPER = UserMapper.INSTANCE;

    @Autowired
    private UserService userService;


    @PostMapping("/donor")
    @ApiOperation(value = "donor signup", response = UserResDTO.class)
    public ResponseEntity<UserResDTO> donorSignUp(@Valid @RequestBody UserReqDTO userAddDto)
            throws CustomException, ResourceNotFoundException {
        log.info("calling user donor signup API");
        validateUserAddDTO(userAddDto, UserRole.DONOR);
        User user = MAPPER.userAddDTOToUser(userAddDto);
        User returnUser = userService.addUser(user);
        UserResDTO userResponse = MAPPER.userToUserResponse(returnUser);
        return new ResponseEntity<UserResDTO>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/innovator")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "innovator signup", response = UserResDTO.class)
    public ResponseEntity<UserResDTO> innovatorSignUp(@Valid @RequestBody UserReqDTO userAddDto)
            throws CustomException, ResourceNotFoundException {
        log.info("calling user innovator signup API");
        validateUserAddDTO(userAddDto, UserRole.INNOVATOR);
        User user = MAPPER.userAddDTOToUser(userAddDto);
        User returnUser = userService.addUser(user);
        UserResDTO userResponse = MAPPER.userToUserResponse(returnUser);
        return new ResponseEntity<UserResDTO>(userResponse, HttpStatus.CREATED);
    }

    private void validateUserAddDTO(UserReqDTO userAddDto, UserRole userRole) throws CustomException {
        if (userAddDto.getRole()!=null &&  !userRole.equals(userAddDto.getRole()))
            throw new CustomException("Bad Request", HttpStatus.BAD_REQUEST, "You can create account for "+ userRole.name() +" only...!");
    }
}