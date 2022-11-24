package com.mytech.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-21
 * @description :
 */
public class ProductionPreciseShardingAlgorithmTB implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        // 1. get the barcode from shardingValue;
        String barcode = shardingValue.getValue();
        // 2. trim barcode to get the last 3 bit and parse into Integer
/*      //this is for old version that use the   last 3 bit as barcode number
        String barcodeNumberStr = barcode.substring(barcode.length() - 3, barcode.length());
        int barcodeNumber = Integer.parseInt(barcodeNumberStr);*/

        //this is for new version that use "_" as  barcode number separator
        String[] split = barcode.split("_");

        int barcodeNumber = Integer.parseInt((String) Array.get(split, split.length - 1));

        // 3. mod4 to get table index
        Integer tableIndex = barcodeNumber % 4;
        //4. assembly tableName
        // 5. return node string
        String nodeName=assembleTableName("t_production_result_2021_",tableIndex);
        if (nodeName!=null && availableTargetNames.contains(nodeName)){
            return nodeName;
        }else{
            throw new RuntimeException("no target table matches nodeName;");
        }//if-else
    }//doSharding
    private String assembleTableName(String prefix, Integer tableIndex) {
        String tableName=prefix+tableIndex;
//        if(!isValid(tableName,"regEx")) throw new IllegalArgumentException("tableName contains Illegal string that may cause SQL injection;");
        return tableName;
    }//assemblyTableName
}//ProductionPreciseShardingAlgorithmTB
