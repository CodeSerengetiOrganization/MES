package com.mytech.config;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-06-29
 * @description :THis HashMap store mapping of orderName and barcodes which are in the orderName
 * key:orderName;value:a Set of barcodes;
 *
 */
@Component
public class ActiveOrderSetConfig {
    private static Map<String, Set<String>> activeOrders=new HashMap<>();

    public boolean addBarcode(String orderName, String barcode){
        System.out.println("from ActiveOrderSetConfig, orderName:"+orderName);
        Preconditions.checkNotNull(orderName,"orderName is null");
        Preconditions.checkArgument(!orderName.isEmpty(),"orderName is empty");
        Preconditions.checkNotNull(barcode,"barcode is null");
        Preconditions.checkArgument(!barcode.isEmpty(),"barcode is empty");

        boolean result=false;
        if(activeOrders.containsKey(orderName)){
            //add to an existing set
            Set<String> activeOrder=activeOrders.get(orderName);
            result = activeOrder.add(barcode);
        }else{
            //add both orderName and barcode
            HashSet<String> barcodes=new HashSet<String>();
            barcodes.add(barcode);
            activeOrders.put(orderName,barcodes);
            result=true;
        }
        return result;


/*        if(!activeOrders.containsKey(orderName)) return false;
        Set<String> activeOrder=activeOrders.get(orderName);
        boolean result = activeOrder.add(barcode);
        return result;*/
    }//addBarcode
    public boolean addActiveOrder(String orderName, Set<String> orderSet){
        boolean result=false;
        if(activeOrders.containsKey(orderName)){
            //add set members into existing set
            result=true;
        }else{
            //add a new set
            Set<String> putOrderSet = activeOrders.put(orderName, orderSet);
            result=true;
        }
        return result;
    }//addActiveOrder

    public Set<String> getBarcodes(String orderName){
        Preconditions.checkNotNull(orderName,"orderName is null");
        Preconditions.checkArgument(!orderName.isEmpty(),"orderName is empty");
        return activeOrders.get(orderName);
    }//getBarcodes

    public Set<String> getOrders() {
        return activeOrders.keySet();
    }//getOrders

    public Map<String, Set<String>> getActiveOrderBarcodes(){
        return activeOrders;
    }//getActiveOrderBarcodes

    /**
     * This method is only a simulation for test
     */
    public void loadDefaultMapping(){
        String orderName="order202106";
        addBarcode(orderName,"scu002");
    }//loadDefaultMapping
}//ActiveOrderSetConfig
