package com.sample.springboot.microservices.common.code.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author Manjunath Asundi
 */

@Setter
@Getter
@ToString
@Data
public class ProjectReqDTO implements Serializable {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private float required_amount;

   }