package com.mytech.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-06-30
 * @description :key:currentStation; value:ValidPreviousStatus that could allow the part
 * to be processed on this machine station
 */
@Component
public class CurrentStationToPreviousValidStatusMapping extends HashMap<String, String> {

    public void loadDefaultMapping(){
        this.put("LoadStation","INIT");
        this.put("CoatingStation","LOAD_OK");
        this.put("CuttingStation","COAT_OK");
        this.put("AssemblyStation","CUT_OK");
        this.put("EOLStation","ASSEMBLE_OK");
        this.put("PackagingStation","EOL_OK");
    }//loadDefaultMapping
}//CurrentStationToPreviousValidStatusMapping
