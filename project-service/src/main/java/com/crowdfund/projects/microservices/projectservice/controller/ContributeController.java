package com.crowdfund.projects.microservices.projectservice.controller;


import com.crowdfund.projects.microservices.common.code.dto.*;
import com.crowdfund.projects.microservices.common.code.exception.CustomException;
import com.crowdfund.projects.microservices.common.code.exception.ResourceNotFoundException;
import com.crowdfund.projects.microservices.projectservice.service.ContributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1/project-service/contribute", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Contribute", description = "Contribute operations")
@Validated
@Slf4j
public class ContributeController {

    @Autowired
    private ContributeService contributeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_INNOVATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DONOR')")
    @ApiOperation(value = "saveContribute", response = TransactionResDTO.class)
    public ResponseEntity<TransactionResDTO> saveContribute(@Valid @RequestBody TransactionReqDTO transactionReqDTO, OAuth2Authentication authentication)
            throws CustomException, ResourceNotFoundException {
        log.info("calling save contribute API");
        TransactionResDTO transactionResDTO = contributeService.saveContribute(transactionReqDTO, authentication);
        return new ResponseEntity<TransactionResDTO>(transactionResDTO, HttpStatus.CREATED);
    }

}
