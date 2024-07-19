package com.crowdfund.projects.microservices.common.code.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Manjunath Asundi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {
    private String area;

    private String city;

    private String pincode;

    private String state;

    private String country;
}