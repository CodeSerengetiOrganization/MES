package com.mytech.controller;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.mytech.domain.ProductionResult;
import com.mytech.dto.ProductionResultInputDTO;
import com.mytech.service.ProductionService;
import com.mytech.util.ProductionResultUtils;

import javax.validation.Valid;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-13
 * @description :
 */
@RestController
@Scope("prototype")
@RequestMapping("/mes")
public class ProductionController {
    @Autowired
    ProductionService service;

    @PostMapping("/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductionResult save(@RequestBody @Valid ProductionResultInputDTO inputDTO){
        Preconditions.checkNotNull(inputDTO,"inputDTO is null");
        ProductionResult result = ProductionResultUtils.convertToProductionResult(inputDTO);
        return service.save(result);
    }//save



}//ProductionController
