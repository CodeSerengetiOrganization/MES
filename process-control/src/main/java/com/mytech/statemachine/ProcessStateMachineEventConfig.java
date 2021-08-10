package com.mytech.statemachine;


import com.mytech.domain.ProcessStateEnum;
import com.mytech.domain.ProductManufacturingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import com.mytech.service.ManufacturingStatusService;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-23
 * @description :
 */
@WithStateMachine(id="ProcessStateMachine")//ProcessStateMachine
public class ProcessStateMachineEventConfig {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    ManufacturingStatusService manufacturingService;


    @OnTransition(target = "LOAD_OK")
    public void loadPart_Pass(Message message){
        System.out.println("Load Part successfully");
        logger.info("Load Part successfully");
        doUpdateManufacturingStatus(message, ProcessStateEnum.LOAD_OK);

    }//loadPart_Pass
    @OnTransition(target = "LOAD_NG")
    public void loadPart_Fail(Message message){
        System.out.println("Load Part failed");
        logger.info("Load Part failed");
        doUpdateManufacturingStatus(message,ProcessStateEnum.LOAD_NG);
    }//loadPart_Fail

    @OnTransition(target = "COAT_OK")
    public void coatPart_Pass(Message message){
        System.out.println("Coat Part successfully");
        logger.info("Coat Part successfully");
        doUpdateManufacturingStatus(message,ProcessStateEnum.COAT_OK);
    }//coatPart_Pass
    @OnTransition(target = "COAT_NG")
    public void coatPart_Fail(Message message){
        System.out.println("Coat Part failed");
        logger.info("Coat Part failed");
        doUpdateManufacturingStatus(message,ProcessStateEnum.COAT_NG);
    }//loadPart_Fail

    @OnTransition(target = "CUT_OK")
    public void cutPart_Pass(Message message){
        logger.info("Cut Part successfully");
        doUpdateManufacturingStatus(message,ProcessStateEnum.CUT_OK);
    }//cutPart_Pass

    @OnTransition(target = "CUT_NG")
    public void cutPart_Fail(Message message){
        logger.info("Cut Part failed");
        doUpdateManufacturingStatus(message,ProcessStateEnum.CUT_NG);
    }//cutPart_Fail

    @OnTransition(target = "ASSEMBLE_OK")
    public void assemblePart_Pass(Message message){
        logger.info("Assemble Part successfully");
        doUpdateManufacturingStatus(message,ProcessStateEnum.ASSEMBLE_OK);
    }//assemblePart_Pass

    @OnTransition(target = "ASSEMBLE_NG")
    public void assemblePart_Fail(Message message){
        logger.info("Assemble Part failed");
        doUpdateManufacturingStatus(message,ProcessStateEnum.ASSEMBLE_NG);
    }//assemblePart_Fail

    //EOL related
    @OnTransition(target = "EOL_OK")
    public void eolTest_Pass(Message message){
        logger.info("EOL test successfully");
        doUpdateManufacturingStatus(message,ProcessStateEnum.EOL_OK);
    }//eolTest_Pass

    @OnTransition(target = "EOL_NG")
    public void eolTest_Fail(Message message){
        logger.info("EOL test failed");
        doUpdateManufacturingStatus(message,ProcessStateEnum.EOL_NG);
    }//eolTest_Fail

    //packaging related
    @OnTransition(target = "PACKAGE_OK")
    public void packagePart_Pass(Message message){
        logger.info("Package Part successfully");
        doUpdateManufacturingStatus(message,ProcessStateEnum.PACKAGE_OK);
    }//packagePart_Pass

    @OnTransition(target = "PACKAGE_NG")
    public void packagePart_Fail(Message message){
        logger.info("Package Part failed");
        doUpdateManufacturingStatus(message,ProcessStateEnum.PACKAGE_NG);
    }//packagePart_Fail

    private ProductManufacturingStatus doUpdateManufacturingStatus(Message message, ProcessStateEnum targetStatus) {
        System.out.println("From Eventconfig, event:"+message.getPayload());
        String barcode=((ProductManufacturingStatus)message.getHeaders().get("productManufacturingStatus")).getBarcode();
        ProductManufacturingStatus status=manufacturingService.saveStatus(barcode,targetStatus);
        return status;
    }//doUpdateManufacturingStatus




/*    @OnTransition(target = "CUT_OK")
//    public void cutPart_Pass(Map<String,String> msg){
//    public void cutPart_Pass(ProcessEventEnum event){
        public void cutPart_Pass(){
        logger.info("Cut Part successfully");
//        ProcessStateMachineBuilder.
//        System.out.println("From Eventconfig, msg:"+msg.toString());
        System.out.println("From Eventconfig, event:"+"event");
//        manufacturingService.updateManufacturingStatus();
    }//cutPart_Pass*/

/*    @OnTransition(target = "CUT_OK")
    public void cutPart_Pass(Message message){
        logger.info("Cut Part successfully");
        System.out.println("From Eventconfig, event:"+message.getPayload());
        manufacturingService.saveStatusToDatabase(message);
    }//cutPart_Pass*/
}
