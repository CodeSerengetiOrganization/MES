package com.mytech.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-06-30
 * @description :key:currentStation;value:an active order
 */
@Component
public class StationOrderMaping extends HashMap<String, String> {

    /**
     * This method A default mapping of a current station and active orders.
     * This method is just for simulation.
     */
    public void loadDefaultMapping(){
        this.put("CoatingStation","order202106");
        this.put("CuttingStation","order202106");
    }//loadDefaultMapping
}//StationOrderMaping
