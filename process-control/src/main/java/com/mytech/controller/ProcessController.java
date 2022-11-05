package com.mytech.controller;

import com.google.common.base.Preconditions;
import com.mytech.domain.ProcessEventEnum;
import com.mytech.domain.ProcessStateEnum;
import com.mytech.dto.StateInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import com.mytech.service.ManufacturingProcessService;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-04
 * @description :
 */
@RestController
@Scope("prototype")
@RequestMapping("/mes")
public class ProcessController {
    @Autowired
    ManufacturingProcessService service;

    @GetMapping("/production/v1/{barcode},{currentStation}")
    public int queryPartStatus(@PathVariable String barcode, @PathVariable String currentStation){
        Preconditions.checkNotNull(barcode,"barcode from "+currentStation+" is null");
        if(barcode.isEmpty()) throw new RuntimeException("barcode from "+currentStation+" is empty");
        int result=service.isValidToProcess(barcode,currentStation);
        return result;
    }//queryPartStatus

    /**
     * This controller receive manufacturing result,then do two things:1. update manufacturing
     * status;2. save manufacturing result.
     * @param msg
     * @return
     */
    @PutMapping("/production/v1/processManufacturingResult")
    public Boolean processManufacturingResult(@RequestBody Map<String,String> msg){
        System.out.println("From testController, Map msg:"+msg.toString());
        String barcode=msg.get("barcode");
        String newEvent=msg.get("event");
        Boolean aBoolean = service.processManufacturingResult(barcode, newEvent);
        return aBoolean;
/*        manufacturingService.updateManufacturingStatus(msg);
        Message<ProcessEventEnum> message = MessageBuilder.withPayload(ProcessEventEnum.valueOf(msg.get("event"))).setHeader("barcode", msg.get("barcode")).build();
        manufacturingService.updateManufacturingStatusMessage(message);*/
    }//updateManufacturingStatusj

    @PutMapping("/production/v1/productstate")
    public void processProductState(@RequestBody StateInputDTO inputDTO){
        ProcessEventEnum event = inputDTO.getEvent();
        //build a Message
        Message<ProcessEventEnum> processStateEnumMessage = MessageBuilder
                .withPayload(event)
                .setHeader("stationName",inputDTO.getStationName())
                .setHeader("barcode",inputDTO.getBarcode())
                .build();
        //send to service to process, which will use state machine
        MessageHeaders headers = processStateEnumMessage.getHeaders();
        String barcodeStr= (String) headers.get("barcode");
        String stationNameStr = headers.get("stationName", String.class);
        String eventStr=processStateEnumMessage.getPayload().toString();
//        String barcodeStr=processStateEnumMessage.getHeaders();
        boolean result = service.processManufacturingResult(barcodeStr, eventStr);
        System.out.println("event Enum:"+event);
        System.out.println("inputDTO:"+inputDTO.toString());
    }

    /**
     * This is a simple test method to see if the controller could parse a ProcessStateEnum object from JSON string
     * @param inputDTO
     */
    @PutMapping("/production/v1/getInputDtoWithEnum")
    public void getInputDtoWithEnum(@RequestBody StateInputDTO inputDTO){
        ProcessEventEnum event = inputDTO.getEvent();
        System.out.println("event Enum:"+event);
        System.out.println("inputDTO:"+inputDTO.toString());
    }
}//class
