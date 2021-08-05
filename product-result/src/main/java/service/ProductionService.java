package src.main.java.service;



import src.main.java.domain.ProductionResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-05
 * @description :
 */
public interface ProductionService {
    //1. create
    ProductionResult save(ProductionResult result);

    //2. Retrieve
    List<ProductionResult> findAll();
    List<ProductionResult> findByBarcode(String barcode);
    List<ProductionResult> findAllByStation(String station);

    List<ProductionResult > findByBarcodeAndResult(String barcode, Integer result);
    List<ProductionResult > findAllByCreateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<ProductionResult > findByOrderName(String orderName);




    //3. update--not need in this system

    //4. delete--not need in this system



//6. for test methods
//    String testSave(String barcode) throws InterruptedException;

}//ProductionService
