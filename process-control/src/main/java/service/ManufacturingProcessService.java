package src.main.java.service;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-03
 * @description :an interface to provide service to manufacturing process control
 */
public interface ManufacturingProcessService {
    //check if the barcode at currentStation is good to go
    int isValidToProcess(String barcode, String currentStation);
}//interface
