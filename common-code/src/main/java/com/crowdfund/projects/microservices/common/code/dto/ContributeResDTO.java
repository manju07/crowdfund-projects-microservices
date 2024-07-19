package com.crowdfund.projects.microservices.common.code.dto;

import com.crowdfund.projects.microservices.common.code.constant.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributeResDTO implements Serializable {
    @NotEmpty
    private Long id;
    @NotEmpty
    private float amount;

    @NotEmpty
    private PaymentStatus paymentStatus;

    @NotEmpty
    private Long projectId;
}