package com.mytech.controller;

import com.google.common.collect.Maps;
import com.mytech.domain.ProcessEventEnum;
import com.mytech.domain.ProcessStateEnum;
import com.mytech.domain.ProductionResult;
import com.mytech.service.ManufacturingProcessService;
import com.mytech.service.ManufacturingStatusService;
import com.mytech.service.ProductionService;
import com.mytech.simulator.ManufacturingLineSimulator;

import com.mytech.util.BeanContext;
import lombok.SneakyThrows;
import lombok.Synchronized;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-31
 * @description :
 */

@RestController
@RequestMapping("mes/test")
public class ManufacturingStationSimulatorController {
//    Thread thread1=new Thread(()->{System.out.println(Thread.currentThread().getName()+" has been started via lambda expression");});
//    new Runnable()



    @GetMapping("/begine")
    public void begineSimulation() throws InterruptedException {
        //load Station
        LoadRunnable loadRunnable = new LoadRunnable();
        Thread loadStation = new Thread(loadRunnable, "LoadStation");
        loadStation.start();
        //Thread.sleep(2000);
        //Coating Station;
        CoatingRunnable coatingRunnable=new CoatingRunnable();
        Thread coatingStation=new Thread(coatingRunnable,"CoatingStation");
        coatingStation.start();

        //cutting machine station;
        Thread.sleep(5000);//cutting machine starts 5 seconds after its previous station
        CutRunnable cutRunnable=new CutRunnable();
        Thread cuttingStation = new Thread(cutRunnable, "CuttingStation");
        cuttingStation.start();

        //Assemble machine station;
        Thread.sleep(2000);//Assemble machine starts 2 seconds after its previous station
        AssembleRunnable assembleRunnable=new AssembleRunnable();
        Thread assembleStation = new Thread(assembleRunnable, "AssembleStation");
        assembleStation.start();
    }//begineSimulation

}//ManufacturingStationSimulatorController

@Component
class LoadRunnable implements Runnable {
    /*    Load station（虚拟进程）会做两件事情：1. 把barcode存入Production_result表，记录一行loade result,结果OK；
     2. 后台根据该条码在product_manufacturing_status中新建该条码记录，设置为INIT，启动state machine, 并发送 LOAD_PASS，
     触发状态机转移到LOAD_OK;或直接设置为LOAD_OK;--因为load之前，系统无法获知这些条码。


     */
    private ManufacturingLineSimulator simulator;
    private ManufacturingStatusService statusService;
    private ProductionService productionService;

    @SneakyThrows
    @Override
    @Synchronized
    public void run() {
        this.simulator= BeanContext.getApplicationContext().getBean(ManufacturingLineSimulator.class);
        this.statusService=BeanContext.getApplicationContext().getBean(ManufacturingStatusService.class);
        this.productionService=BeanContext.getApplicationContext().getBean(ProductionService.class);
        for (int i = 0; i <20 ; i++) {
            String barcode= "scu_"+i;
            ProductionResult productionResult = ProductionResult.builder().barcode(barcode)
                    .orderNumber("simulated order")
                    .stationName("Load station")
                    .result(1)
                    .modifyTime(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .comment("by LoadRunnable Thread").build();
            productionService.save(productionResult);
            System.out.println("save barcode ["+barcode+"] into Production_result table: load result:LOAD_PASS");
            statusService.saveInitStatus(barcode, ProcessStateEnum.LOAD_OK);
            System.out.println("save barcode ["+barcode+"] into Product_manufacturing_status table: LOAD_OK");
//            simulator.addToBeforeCoatingList(barcode);
            simulator.addToNextStationList(simulator.beforeCoatingList,barcode);
            System.out.println("Load station interval begins....");
            Thread.sleep(500);
            System.out.println("Load station interval ends....");
        }//for
//        List<String> listAllBeforeCoating = simulator.listAllBeforeCoating();
        String listAllBeforeCoating = simulator.listAllStationList(simulator.beforeCoatingList);
        System.out.println("List all barcode BeforeCoating:"+listAllBeforeCoating);

    }//run
}//LoadRunnable

class CoatingRunnable implements Runnable {
    private ManufacturingLineSimulator simulator;
//    private ManufacturingStatusService statusService;
    private ProductionService productionService;
    private ManufacturingProcessService manufacturingProcessService;
/*    后面各站：虚拟站点，用simulator转移条码，后台则用statemachine处理这些条码；*/

    @SneakyThrows
    @Override
    @Synchronized
    public void run() {
        this.simulator= BeanContext.getApplicationContext().getBean(ManufacturingLineSimulator.class);
        this.manufacturingProcessService=BeanContext.getApplicationContext().getBean(ManufacturingProcessService.class);
        this.productionService=BeanContext.getApplicationContext().getBean(ProductionService.class);
        MachineStationOperation machineStationOperation=new MachineStationOperation();
        machineStationOperation.machineOperation(simulator,manufacturingProcessService,productionService,
                "CoatingStation","CuttingStation",simulator.beforeCoatingList,
                simulator.beforeCutList, ProcessEventEnum.COAT_PASS,1000);
/*        while(true) {
            String barcode = simulator.getFromBeforeCoatingList();
            if (barcode != null) {
                //1. assembly json message with barcode and event
                HashMap<String, String> msgMap = Maps.newHashMap();
                msgMap.put("barcode", barcode);
                msgMap.put("event", "COAT_PASS");
                String jsonString = JSONObject.toJSONString(msgMap);
                //2.send message to controller--for future use
                System.out.println("Coating station send to controller:" + jsonString);
                statusService.updateManufacturingStatus(barcode, ProcessEventEnum.COAT_PASS.toString());
                //3. remove this barcode from list
                simulator.removeFromBeforeCoatingList(barcode);
                System.out.println("Coating station has removed this barcode from BeforeCoatingList:"+barcode);
                System.out.println("the BeforeCoatingList now is: "+simulator.listAllBeforeCoating());
                simulator.addToBeforeCutList(barcode);
                System.out.println("Coating station has added this barcode to BeforeCutList:"+barcode);
                System.out.println("the BeforeCutList now is: "+simulator.listAllBeforeCutList());
//                simulator.addToBizList(beforeCutList,barcode);//BeforeCutList(barcode);
                System.out.println("Coating station interval begins...");
                Thread.sleep(1000);
                System.out.println("Coating station interval ends...");
            }//if
        }//while*/
    }//run
}//CoatingRunnable

//Cutting machine station simulator
class CutRunnable implements Runnable {
    private ManufacturingLineSimulator simulator;
//    private ManufacturingStatusService statusService;
    private ProductionService productionService;
    private ManufacturingProcessService manufacturingProcessService;
    /*    Cutting machine:，use simulator to remove barcode from beforeCutList and add to beforeAssembleList
    ，后台则用statemachine处理这些条码；*/

    @SneakyThrows
    @Override
    @Synchronized
    public void run() {
        this.simulator= BeanContext.getApplicationContext().getBean(ManufacturingLineSimulator.class);
        this.manufacturingProcessService=BeanContext.getApplicationContext().getBean(ManufacturingProcessService.class);
        this.productionService=BeanContext.getApplicationContext().getBean(ProductionService.class);
        MachineStationOperation machineStationOperation=new MachineStationOperation();
        machineStationOperation.machineOperation(simulator,manufacturingProcessService,productionService,
                "CuttingStation","AssembleStation",simulator.beforeCutList,
                simulator.beforeAssembleList, ProcessEventEnum.CUT_PASS,1000);
/*        String stationName="CuttingStation";
        while(true) {
            String barcode = simulator.getFromBeforeCutList();
            if (barcode != null) {
                //1. assembly json message with barcode and event
                HashMap<String, String> msgMap = Maps.newHashMap();
                msgMap.put("barcode", barcode);
                msgMap.put("event", "COAT_PASS");
                String jsonString = JSONObject.toJSONString(msgMap);
                //2.send message to controller--for future use
                System.out.println(stationName+" send to controller:" + jsonString);
                statusService.updateManufacturingStatus(barcode, ProcessEventEnum.CUT_PASS.toString());
                //3. remove this barcode from list
                simulator.removeFromBeforeCutList(barcode);
                System.out.println(stationName+" has removed this barcode from BeforeCoatingList:"+barcode);
                System.out.println("the BeforeCutList now is: "+simulator.listAllBeforeCut());
                simulator.addToBeforeAssmbleList(barcode);
                System.out.println(stationName+" has added this barcode to BeforeCutList:"+barcode);
                System.out.println("the BeforeAssembleList now is: "+simulator.listAllBeforeAssembleList());
//                simulator.addToBizList(beforeCutList,barcode);//BeforeCutList(barcode);
                System.out.println(stationName+" interval begins...");
                Thread.sleep(1000);
                System.out.println(stationName+" interval ends...");
            }//if
        }//while*/
    }//run
}//CutRunnable

class AssembleRunnable implements Runnable {
    private ManufacturingLineSimulator simulator;
    private ManufacturingStatusService statusService;
    private ProductionService productionService;
    private ManufacturingProcessService manufacturingProcessService;

    @SneakyThrows
    @Override
    @Synchronized
    public void run() {
        this.simulator= BeanContext.getApplicationContext().getBean(ManufacturingLineSimulator.class);
        this.statusService=BeanContext.getApplicationContext().getBean(ManufacturingStatusService.class);
        this.productionService=BeanContext.getApplicationContext().getBean(ProductionService.class);
        MachineStationOperation machineStationOperation=new MachineStationOperation();
        machineStationOperation.machineOperation(simulator,manufacturingProcessService,productionService,
                "AssembleStation","EOLTester",simulator.beforeAssembleList,
                simulator.beforeEolList,ProcessEventEnum.ASSEMBLE_PASS,1000);
    }//run
}//Runnable

class MachineStationOperation{
    public  void machineOperation(ManufacturingLineSimulator simulator, ManufacturingProcessService manufacturingProcessService,
                                  ProductionService productionService, String stationName, String nextStationName,
                                  List<String> stationList, List<String> nextStationList, ProcessEventEnum event,
                                  Integer sleepTime) throws InterruptedException {
        while(true) {
            String barcode = simulator.getFromBeforeStationList(stationList);
            if (barcode != null) {
                //1. assembly json message with barcode and event
                HashMap<String, String> msgMap = Maps.newHashMap();
                msgMap.put("barcode", barcode);
                msgMap.put("event", event.toString());
                String jsonString = JSONObject.toJSONString(msgMap);
                //2.send message to controller--for future use
                System.out.println(stationName+" send to controller:" + jsonString);
                manufacturingProcessService.processManufacturingResult(barcode, event.toString());
                //3. remove this barcode from list
                simulator.removeFromBeforeStationList(stationList,barcode);
                System.out.println(stationName+" has removed this barcode:"+barcode);
                System.out.println("the "+stationName+" List now is: "+simulator.listAllStationList(stationList));
                simulator.addToNextStationList(nextStationList,barcode);
                System.out.println(stationName+" has added this barcode to  nextStation:"+barcode);
                System.out.println("the  "+nextStationName+" List now is: "+simulator.listAllStationList(nextStationList));
//                simulator.addToBizList(beforeCutList,barcode);//BeforeCutList(barcode);
                System.out.println(stationName+" interval begins...");
                Thread.sleep(sleepTime);
                System.out.println(stationName+" interval ends...");
            }//if
        }//while
    }
}//MachineStationOperation