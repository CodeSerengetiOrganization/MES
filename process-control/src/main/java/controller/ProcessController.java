package controller;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import service.ManufacturingProcessService;

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
}//class
