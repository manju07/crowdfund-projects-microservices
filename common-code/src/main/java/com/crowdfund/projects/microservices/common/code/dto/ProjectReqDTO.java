package com.crowdfund.projects.microservices.common.code.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author Manjunath Asundi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectReqDTO implements Serializable {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private float required_amount;

   }