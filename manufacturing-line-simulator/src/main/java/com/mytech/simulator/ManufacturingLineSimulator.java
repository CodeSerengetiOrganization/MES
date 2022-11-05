package com.mytech.simulator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-31
 * @description :
 */
@Component
public class ManufacturingLineSimulator {
    public List<String> beforeCoatingList= Lists.newArrayList();
    public List<String> beforeCutList= Lists.newArrayList();
    public List<String> beforeAssembleList= Lists.newArrayList();
    public List<String> beforeEolList= Lists.newArrayList();
    public List<String> beforePackageList= Lists.newArrayList();

/*    //Coating machine operations
    public void addToBeforeCoatingList(String barcode){
        beforeCoatingList.add(barcode);
    }//addToBeforeCoatingList

    public void removeFromBeforeCoatingList(String barcode){
        beforeCoatingList.remove(barcode);
    }//removeFromBeforeCoatingList

    public String getFromBeforeCoatingList(){
*//*        if (beforeCoatingList.size()>0) return beforeCoatingList.get(0);
        return null;*//*
        return getFromMachineList(beforeCoatingList);
    }//getFromBeforeCoatingList

    public List<String> listAllBeforeCoating(){
        return beforeCoatingList;
    }//listAllBeforeCoating

    public Boolean addToBeforeCutList(String barcode) {
        return addToTargetList(beforeCutList,barcode);
    }//addToBeforeCutList*/



/*//Cut machine operations
    public String getFromBeforeCutList() {
        return getFromMachineList(beforeCutList);
    }//getFromBeforeCutList
    public String listAllBeforeCut() {
        return listAllMachineStationList(beforeCutList);
    }//listAllBeforeCut

    public Boolean removeFromBeforeCutList(String barcode) {
        return removeFromTargetList(beforeCutList,barcode);
    }//removeFromBeforeCutList
    public Boolean addToBeforeAssmbleList(String barcode) {
        return addToTargetList(beforeAssembleList,barcode);
    }//addToBeforeAssmbleList
    public String listAllBeforeAssembleList() {
        return listAllMachineStationList(beforeAssembleList);
    }//listAllBeforeAssembleList*/

/*    // util method
    private String getFromMachineList(List<String> beforeMachineList) {
        Preconditions.checkNotNull(beforeMachineList,beforeMachineList.getClass().getName()+" is null");
        if (beforeCoatingList.size()>0) return beforeMachineList.get(0);
        return null;
    }

    private Boolean addToTargetList(List<String> beforeCutList, String barcode) {
        Preconditions.checkNotNull(beforeCutList,"beforeCutList is null");
        return beforeCutList.add(barcode);
    }//addToTargetList

    private Boolean removeFromTargetList(List<String> beforeCutList, String barcode) {
        Preconditions.checkNotNull(beforeCutList,"beforeCutList is null");
        return beforeCutList.remove(barcode);
    }//removeFromTargetList
    private String listAllMachineStationList(List<String> machineStationList) {
        Preconditions.checkNotNull(machineStationList,machineStationList.getClass().getName()+"is nul");
        return machineStationList.toString();
    }//listAllMachineStationList*/



/*    public String listAllBeforeCutList() {
        return beforeCutList.toString();
    }//getFromBeforeCutList*/


    //new utils

    /**
     *
     * @param stationList
     * @return
     * if it works it will replace getFromMachineList
     */
    public String getFromBeforeStationList(List<String> stationList) {
        Preconditions.checkNotNull(stationList,stationList.getClass().getName()+"is null;");
        if (stationList.size()>0) return stationList.get(0);
        return null;
    }//getFromBeforeStationList

    public Boolean removeFromBeforeStationList(List stationList, String barcode) {
        Preconditions.checkNotNull(stationList,stationList.getClass().getName()+"is null;");
        return stationList.remove(barcode);
    }//removeFromBeforeStationList

    public String listAllStationList(List<String> stationList) {
        return stationList.toString();
    }//listAllStationList

    public Boolean addToNextStationList(List<String> nextStationList, String barcode) {
        Preconditions.checkNotNull(nextStationList,nextStationList.getClass().getName()+"is null;");
        return nextStationList.add(barcode);
    }//listAllStationList
}//ManufacturingLineSimulator
