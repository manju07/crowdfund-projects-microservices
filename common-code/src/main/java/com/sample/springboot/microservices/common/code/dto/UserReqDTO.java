package com.sample.springboot.microservices.common.code.dto;

import com.sample.springboot.microservices.common.code.constant.UserRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * User Add Dto 
 * 
 * @author Manjunath Asundi
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserReqDTO {


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

    @NotEmpty(message = "role is empty")
    private UserRole role;

    @NotEmpty(message = "address is empty")
    private AddressDTO address;
}