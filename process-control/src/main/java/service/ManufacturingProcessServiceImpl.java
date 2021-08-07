package service;

import domain.ProductManufacturingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-03
 * @description :
 */
@Service
public class ManufacturingProcessServiceImpl implements ManufacturingProcessService {
    @Autowired
    ManufacturingStatusService manufacturingStatusService;
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
