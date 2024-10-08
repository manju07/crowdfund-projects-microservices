package com.crowdfund.projects.microservices.common.code.dto;

import com.crowdfund.projects.microservices.common.code.constant.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Manjunath Asundi
 */

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectResDTO implements Serializable {

    private Long id;
    private String name;
    private String description;
    private float requiredAmount;
    private float receivedAmount;
    private ProjectStatus status;

}