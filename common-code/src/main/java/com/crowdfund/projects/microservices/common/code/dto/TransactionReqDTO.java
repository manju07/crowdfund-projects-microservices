package com.crowdfund.projects.microservices.common.code.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Manjunath Asundi
 */

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionReqDTO implements Serializable {

    @NotNull(message = "amount is missing/null")
    private float amount;

    @NotNull(message = "projectId is missing/null")
    private Long projectId;

}