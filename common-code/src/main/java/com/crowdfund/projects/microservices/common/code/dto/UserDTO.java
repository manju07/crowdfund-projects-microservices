package com.crowdfund.projects.microservices.common.code.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * User Dto Entity
 * 
 * @author Manjunath Asundi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;

    @NonNull
    @NotEmpty
    private String fName;

    @NonNull
    @NotEmpty
    private String lName;

    @NonNull
    @NotEmpty
    private String phone;

    @NonNull
    @NotEmpty
    private String userName;

    @Email
    private String email;

    private String gender;

}