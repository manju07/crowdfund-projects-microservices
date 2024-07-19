package com.crowdfund.projects.microservices.common.code.dto;

import com.crowdfund.projects.microservices.common.code.constant.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User Add Dto
 *
 * @author Manjunath Asundi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserReqDTO implements Serializable {


    @NotEmpty(message = "fName is empty")
    private String fName;

    @NotEmpty(message = "lName is empty")
    private String lName;

    @NotEmpty(message = "phone is empty")
    private String phone;
    @NotEmpty(message = "userName is empty")
    private String userName;

    @NotEmpty(message = "email is empty")
    @Email
    private String email;

    @NotEmpty(message = "gender is empty")
    private String gender;

    @NotEmpty(message = "password is empty")
    private String password;
    @NotNull(message = "role is empty")
    @Valid
    private UserRole role;

    @NotNull(message = "address is empty")
    @Valid
    private AddressDTO address;
}