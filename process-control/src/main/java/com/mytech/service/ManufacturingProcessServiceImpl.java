package com.mytech.service;

import com.mytech.config.ActiveOrderSetConfig;
import com.mytech.config.CurrentStationToPreviousValidStatusMapping;
import com.mytech.config.StationOrderMaping;
import com.mytech.domain.ProcessEventEnum;
import com.mytech.domain.ProcessStateEnum;
import com.mytech.domain.ProductManufacturingStatus;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import com.mytech.statemachine.ProcessStateMachineBuilder;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-03
 * @description :
 */
@Service
@Scope("prototype")
public class ManufacturingProcessServiceImpl implements ManufacturingProcessService {
    @Autowired
    ManufacturingStatusService manufacturingStatusService;
    @Autowired
    ProcessStateMachineBuilder processStateMachineBuilder;
    @Autowired
    BeanFactory beanFactory;
    @Resource(name="ManufacturingStatusPersister")
    StateMachinePersister<ProcessStateEnum,ProcessEventEnum,ProductManufacturingStatus> manufacturingStatusPersister;

    @Autowired
    ActiveOrderSetConfig activeOrders;
    @Autowired
    StationOrderMaping stationOrderMaping;
    @Autowired
    CurrentStationToPreviousValidStatusMapping currentStationToPreviousValidStatusMapping;



    @Override
    public int isValidToProcess(String barcode, String currentStation) {
        //1..get currentOrderName with currentStation--from StationOrderMaping
        String currentOrderName=getCurrentOrderName(currentStation);

        //2.check if the barcode belongs to current active order--from ActiveOrderSetConfig
//        if(!isBelongCurrentOrder(barcode)) return -1;
        if(!isBelongCurrentOrder(barcode,currentOrderName)) return -1;

        // 3.acess the database to get the current state,
/*        try {
            StateMachine<ProcessStateEnum, ProcessEventEnum> stateMachine = processStateMachineBuilder.build(beanFactory);
            StateMachine<ProcessStateEnum, ProcessEventEnum> restoredSM = processStateMachinePresister.restore(stateMachine, productStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        ProductManufacturingStatus productManufacturingStatus = manufacturingStatusService.findByBarcode(barcode);
        String productStatus=productManufacturingStatus.getStatus();
        //4.get the criteria state from HashMap
        String validPrevStatus=currentStationToPreviousValidStatusMapping.get(currentStation);
        //5. compare the two and return the result;
//        boolean status_not_expected=false;//just for simulation
        if(!Objects.equals(productStatus,validPrevStatus)) return -2;
        return 0;
    }//isValidToProcess

    /**
     * receive manufacturing result,then do two things:1. update manufacturing status;2. save manufacturing result.
     *
     * @param barcode
     * @param newEvent
     * @return
     */
    @Override
    public boolean processManufacturingResult(String barcode, String newEvent) {


        //1. get status string from database and parse into Enum
/*        ProductManufacturingStatus exampleObj = ProductManufacturingStatus.builder().barcode(barcode).build();
        Example<ProductManufacturingStatus> example= Example.of(exampleObj);
        Optional<ProductManufacturingStatus> one = manufacturingStatusRepo.findOne(example);
        ProductManufacturingStatus productStatus = one.get();*/
        ProductManufacturingStatus productStatus = manufacturingStatusService.findByBarcode(barcode);


        //2. use Persister to restore a state machine;
        ProcessEventEnum targetEvent = ProcessEventEnum.valueOf(newEvent);
        Message<ProcessEventEnum> message = MessageBuilder.withPayload(targetEvent)
                .setHeader("productManufacturingStatus", productStatus).build();
        Boolean eventSent = false;
        try {
            StateMachine<ProcessStateEnum, ProcessEventEnum> stateMachine = processStateMachineBuilder.build(beanFactory);
            StateMachine<ProcessStateEnum, ProcessEventEnum> restoredSM = manufacturingStatusPersister.restore(stateMachine, productStatus);
            //3. send event to this state machine
            eventSent = restoredSM.sendEvent(message);
        } catch (Exception e) {
            throw new RuntimeException(e);// change to Service exception in future
        }//try-catch
//        return eventSent;

        boolean resultSaved = false;
        return eventSent && resultSaved;
    }//processManufacturingResult

    /**
     *
     * @param currentStation the station name that send the query from
     * @return an unique active order name which has the process which contains currentStation
     */
    String getCurrentOrderName(String currentStation){
        //need more real code
        String currentOrder = stationOrderMaping.get(currentStation);
        return currentOrder;
    }//getCurrentOrderName

    /*    boolean isBelongCurrentOrder(String barcode,String currentStation){
        Set<String> barcodes = activeOrders.getBarcodes(currentStation);
        if(barcodes !=null) return barcodes.contains(barcode);
        return false;
    }//isBelongCurrentOrder*/

    boolean isBelongCurrentOrder(String barcode, String currentActiveOrder){
        Set<String> barcodes = activeOrders.getBarcodes(currentActiveOrder);
        if(barcodes !=null) return barcodes.contains(barcode);
        return false;
    }//isBelongCurrentOrder

    /**
     *
     * @param barcode the barcode of the pruduct
     * @return true if the barcode is in one of the active orders; false otherwise;
     */
    boolean isBelongCurrentOrder(String barcode){
        //need more real code
        Set<String> orders=activeOrders.getOrders();
        for(String order:orders){
            Set<String> barcodes=activeOrders.getBarcodes(order);
            if (barcodes.contains(barcode)) return true;
        }//for
        return false;
    }//isBelongCurrentOrder
}//class
