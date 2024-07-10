package com.sample.springboot.microservices.common.code.dto;

import com.sample.springboot.microservices.common.code.constant.ProjectStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author Manjunath Asundi
 */

@Setter
@Getter
@ToString
@Data
public class ProjectResDTO implements Serializable {

    private Long id;
    private String name;
    private String description;
    private float required_amount;
    private float received_amount;
    private ProjectStatus status;


   }