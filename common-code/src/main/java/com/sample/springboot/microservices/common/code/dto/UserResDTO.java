package com.sample.springboot.microservices.common.code.dto;

import lombok.*;

/**
 * UserResponse
 * @author Manjunath Asundi
 */
@Getter
@Setter
@ToString
@Data
public class UserResDTO {

    private Long id;

    private String fName;

    private String lName;

    private String phone;

    private String email;

    private String gender;
}