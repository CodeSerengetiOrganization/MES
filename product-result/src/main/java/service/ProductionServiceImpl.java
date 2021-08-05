package src.main.java.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mytech.config.ActiveOrderSetConfig;
import com.mytech.config.CurrentStationToPreviousValidStatusMapping;
import com.mytech.config.StationOrderMaping;
import com.mytech.domain.ProductManufacturingStatus;


import com.mytech.statemachine.ProcessEventEnum;
import com.mytech.statemachine.ProcessStateEnum;
import com.mytech.statemachine.ProcessStateMachineBuilder;
import src.main.java.domain.ProductionResult;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import src.main.java.repository.ProductionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-05
 * @description :
 */
@Service
@Scope("prototype")
public class ProductionServiceImpl implements ProductionService {

    @Autowired
    ProductionRepository productionRepo;
/*    @Autowired
    ProcessStateMachineBuilder processStateMachineBuilder;
    --to decouple ProductService and state machine*/
    @Autowired
    BeanFactory beanFactory;
    /*@Resource(name="ManufacturingStatusPersister")
    StateMachinePersister<ProcessStateEnum,ProcessEventEnum,ProductManufacturingStatus> processStateMachinePresister;*/


/*    @Autowired
    ActiveOrderSetConfig activeOrders;
    @Autowired
    StationOrderMaping stationOrderMaping;
    @Autowired
    CurrentStationToPreviousValidStatusMapping currentStationToPreviousValidStatusMapping;
    --these were used by isValidToProcess*/
    //1. create
    @Override
    public ProductionResult save(ProductionResult result) {
        Preconditions.checkNotNull(result,"the entity to save is null or empty");
        return productionRepo.save(result);
    }

    //2. Retrieve
    @Override
    public List<ProductionResult> findAll() {
        return null;
    }

    @Override
    public List<ProductionResult> findByBarcode(String barcode) {
        return productionRepo.findByBarcode(barcode);
    }


/*
    @Override
    public List<ProductionResult> findAll(String tableName) {
        return null;
    }
    public List<ProductionResult> findByBarcode(String barcode){
        Preconditions.checkArgument(Strings.isNullOrEmpty(barcode),"barcode is blank");
        String orderName = findOrderName(barcode);
        productionRepo.findAll(orderName);
        return null;
    }//findByBarcode
*/

    @Override
    public List<ProductionResult> findAllByStation(String station) {
        return null;
    }

    @Override
    public List<ProductionResult> findByBarcodeAndResult(String barcode, Integer result) {
        return null;
    }

    @Override
    public List<ProductionResult> findAllByCreateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<ProductionResult> findByOrderName(String orderName) {
        return null;
    }




    //6. other method
    private String findOrderName(String barcode) {
/*        Set<String> activeOrders=new HashSet<String>();
        activeOrders.add("scu20210305");
        activeOrders.add("scu20210306");*/

        //simulate findAllByActive to get all active orders, and findAll() to get all barcodes
        List<String> scu20210301= Lists.newArrayList();
        scu20210301.add("ford_scu_010101");
        scu20210301.add("ford_scu_010102");
        scu20210301.add("ford_scu_010103");

        List<String> scu20210302= Lists.newArrayList();
        scu20210302.add("ford_scu_020101");
        scu20210302.add("ford_scu_020102");
        scu20210302.add("ford_scu_020103");

        Map<String, List<String>> activeOrderMap= Maps.newHashMap();
        activeOrderMap.put("scu20210301",scu20210301);
        activeOrderMap.put("scu20210302",scu20210302);
        String resultOrder="";
        for(String key:activeOrderMap.keySet()){

            if (activeOrderMap.get(key).contains(barcode)) resultOrder=key;
        }
        return resultOrder;
/*        Set<Map.Entry<String, List<String>>> entries = activeOrderMap.entrySet();

        Set<List<String>> activeOrders= Sets.newHashSet();
        activeOrders.add(scu20210301);
        activeOrders.add(scu20210302);
        for (List<String> orderList:activeOrders) {
            if (orderList.contains(barcode)) return orderList.;
        }
        return barcode;*/
    }//findOrderName

    private String assemlyTableName(String prefix, String orderName) {
        String tableName=prefix+orderName;
        if(!isValid(tableName,"regEx")) throw new IllegalArgumentException("tableName contains Illegal string that may cause SQL injection;");
        return tableName;
    }//assemblyTableName

    private boolean isValid(String tableName, String regEx) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(tableName),"tableName to check is null or blank;");
        Preconditions.checkArgument(Strings.isNullOrEmpty(regEx),"regex to match tableName is null or blank;");
        boolean matchResult = Pattern.compile(regEx).matcher(tableName).matches();
        return true;
    }//isValid

//6. for test methods
    private String findOrderNameSimulation(String barcode){
        if ("ford_scu_001".equals(barcode)) {
            return "table001";
        }else if ("ford_scu_002".equals(barcode)){
            return "table002";
        }else{
            return "default table name";
        }
    }//findOrderNameForTest

/*    //6. for test methods
    @Override
    public String testSave(String barcode) throws InterruptedException {
        //1. find which tableName this barcode belongs to
        String orderName = findOrderNameSimulation(barcode);
        //2. assembly SQL line with tableName
        productionRepo.setTableName(orderName);


        System.out.println(Thread.currentThread().getName()+"--Service--testSave begins.Service:"+this.toString());
        System.out.println(Thread.currentThread().getName()+"--Service--before Repository.Service:"+this.toString() +
                "--barcode:"+barcode+";tableName:"+orderName);
        if ("ford_scu_001".equals(barcode)){
            Thread.sleep(10000);
        }
        //3. fire the save request
        productionRepo.saveForTest(orderName);
        System.out.println(Thread.currentThread().getName()+"--Service--after Repository.Service:"+this.toString() +
                "--barcode:"+barcode+";tableName:"+orderName);
        return orderName;
    }//testSave*/


    //7. to delete
/*    @Override
    public ProductionResult save(ProductionResult result) {
        //1. find which tableName this barcode belongs to
        String barCode = result.getBarcode();
        String orderName = findOrderName(barCode);

        //2. assembly SQL line with tableName
        String tableName=assemlyTableName("t_production_result_",orderName);
//        productionRepo.setTableName(orderName);
        //3. fire the save request
        return productionRepo.save(tableName,result);
    }//save*/
}
