package src.main.java.repository;


import src.main.java.domain.ProductionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-05
 * @description :
 */
@RequestScope
public interface ProductionRepository extends JpaRepository<ProductionResult, Integer> {
/*    //1. create
    ProductionResult save(ProductionResult result);
    ProductionResult save(String tableName, ProductionResult result);//this is mess use
    //2. Retrieve
    List<ProductionResult> findAll(String tableName);
    List<ProductionResult> findAllByBarcode(String tableName);*/
    //1. create
    ProductionResult save(ProductionResult result);

    //2. Retrieve
    List<ProductionResult> findAll();
    List<ProductionResult> findByBarcode(String barcode);
    List<ProductionResult> findAllByStationName(String station);
//    List<ProductionResult > findByOrderName(String orderName);
    List<ProductionResult > findByBarcodeAndResult(String barcode, Integer result);
    List<ProductionResult > findAllByCreateTimeBetween(LocalDateTime start, LocalDateTime end);


    //3. update--not need in this system

    //4. delete--not need in this system
/*    @Transactional
    Integer deleteByBarcode(String barcode);*/


    //5. other method
//    void setTableName(String tableName);



/*
    //6. other method for test
    void saveForTest(String tabelName);
*/


}
