package com.crowdfund.projects.microservices.common.code.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Manjunath Asundi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectReqDTO implements Serializable {

    @NotEmpty(message = "name is missing/null")
    private String name;

    @NotEmpty(message = "description is missing/null")
    private String description;

    @NotNull(message = "requiredAmount is null")
    private float requiredAmount;

}