package service;


import com.mytech.repository.ManufacturingStatusRepository;

import com.mytech.statemachine.ProcessStateMachineBuilder;
import domain.ProcessEventEnum;
import domain.ProcessStateEnum;
import domain.ProductManufacturingStatus;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import repository.ManufacturingStatusRepository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-27
 * @description :
 */
@Service
@Scope("prototype")
public class ManufacturingStatusServiceImpl implements ManufacturingStatusService{
    @Autowired
    ManufacturingStatusRepository manufacturingStatusRepo;
    @Autowired
    ProcessStateMachineBuilder processStateMachineBuilder;
    @Autowired
    BeanFactory beanFactory;
    @Resource(name="ManufacturingStatusPersister")
    StateMachinePersister<ProcessStateEnum, ProcessEventEnum, ProductManufacturingStatus> manufacturingStatusPersister;


    @Override
    public ProductManufacturingStatus findByBarcode(String barcode) {
        ProductManufacturingStatus status = manufacturingStatusRepo.findByBarcode(barcode);
        return status;
    }//findByBarcode

    //state machine related
    @Override
    public void updateManufacturingStatus(Map<String, String> msg) {
        //1. get status string from database and parse into Enum
        String barcode=msg.get("barcode");
        String event=msg.get("event");

        ProductManufacturingStatus exampleObj = ProductManufacturingStatus.builder().barcode(barcode).build();
        Example<ProductManufacturingStatus> example= Example.of(exampleObj);
        Optional<ProductManufacturingStatus> one = manufacturingStatusRepo.findOne(example);
        ProductManufacturingStatus productStatus = one.get();
//        productStatus.getStatus()

        //2. use Persister to restore a state machine;
        ProcessEventEnum newEvent=ProcessEventEnum.valueOf(event);
        try {
            StateMachine<ProcessStateEnum, ProcessEventEnum> stateMachine = processStateMachineBuilder.build(beanFactory);
            StateMachine<ProcessStateEnum, ProcessEventEnum> restoredSM = manufacturingStatusPersister.restore(stateMachine, productStatus);
            //3. send event to this state machine
            restoredSM.sendEvent(newEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);// change to Service exception in future
        }//try-catch

    }//updateManufacturingStatus

    @Override
    public Boolean updateManufacturingStatus(String barcode, String newEvent) {
        //1. get status string from database and parse into Enum
        ProductManufacturingStatus exampleObj = ProductManufacturingStatus.builder().barcode(barcode).build();
        Example<ProductManufacturingStatus> example= Example.of(exampleObj);
        Optional<ProductManufacturingStatus> one = manufacturingStatusRepo.findOne(example);
        ProductManufacturingStatus productStatus = one.get();
//        productStatus.getStatus()

        //2. use Persister to restore a state machine;
        ProcessEventEnum targetEvent=ProcessEventEnum.valueOf(newEvent);
        Message<ProcessEventEnum> message = MessageBuilder.withPayload(targetEvent)
                                            .setHeader("productManufacturingStatus", productStatus).build();
        Boolean eventSent=false;
        try {
            StateMachine<ProcessStateEnum, ProcessEventEnum> stateMachine = processStateMachineBuilder.build(beanFactory);
            StateMachine<ProcessStateEnum, ProcessEventEnum> restoredSM = manufacturingStatusPersister.restore(stateMachine, productStatus);
            //3. send event to this state machine
            eventSent= restoredSM.sendEvent(message);
        } catch (Exception e) {
            throw new RuntimeException(e);// change to Service exception in future
        }//try-catch
        return eventSent;
    }//updateManufacturingStatus

    /**
     *restore a state machine from database via barcode, then use this restored state machine send new event
     * In this version, the ProcessEventEnum is carried by a Message object
     * @param message contains a ProcessEventEnum object as payload, and a String as header with key "barcode"
     * @see domain.ProcessStateEnum;
     */
    @Override
    public void updateManufacturingStatusMessage(Message<ProcessEventEnum> message) {
        //1. get status string from database
        String barcode= (String) message.getHeaders().get("barcode");
        ProductManufacturingStatus exampleObj=ProductManufacturingStatus.builder().barcode(barcode).build();
        Example<? extends ProductManufacturingStatus> example= Example.of(exampleObj);
        Optional<? extends ProductManufacturingStatus> one = manufacturingStatusRepo.findOne(example);
        ProductManufacturingStatus productManufacturingStatus = one.get();
        //2. use Persister to restore a state machine;
        try {
            StateMachine<ProcessStateEnum, ProcessEventEnum> stateMachine = processStateMachineBuilder.build(beanFactory);
            StateMachine<ProcessStateEnum, ProcessEventEnum> restoredSM = manufacturingStatusPersister.restore(stateMachine, productManufacturingStatus);
            //3. use restored state machine to send message;
            restoredSM.sendEvent(message);

        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch

    }//updateManufacturingStatusMessage

    /**
     *restore a state machine from database via barcode, then use this restored state machine send new event
     * In this version, the ProcessEventEnum is carried by a Message object
     * @param message contains a ProcessEventEnum object as payload, and a ProductManufacturingStatus as header with key "productManufacturingStatus".
     *                ProductManufacturingStatus contains the old status, which is the same as in database;
     * @see com.mytech.statemachine.ProcessEventEnum
     * @see com.mytech.domain.ProductManufacturingStatus
     */
    @Override
    public ProductManufacturingStatus saveStatusToDatabase(Message message) {
        ProductManufacturingStatus statusFromMsg = (ProductManufacturingStatus) message.getHeaders().get("productManufacturingStatus");
        ProcessEventEnum eventToSave = (ProcessEventEnum) message.getPayload();
        //Assemble ProductManufacturingStatus to save
        ProductManufacturingStatus productManufacturingStatus = ProductManufacturingStatus.builder()
                                                                .barcode(statusFromMsg.getBarcode())
                                                                .status(eventToSave.toString())
                                                                .modifyTime(LocalDateTime.now())
                                                                .comment("modified by state machine").build();
        ProductManufacturingStatus saved = manufacturingStatusRepo.save(productManufacturingStatus);
        Optional<ProductManufacturingStatus> one = manufacturingStatusRepo.findOne(Example.of(saved));

        return one.get();
    }

    /**
     *
     * @param barcode product barcode
     * @param targetStatus the updated state in state machine
     * @return the updated record in database
     */
    @Override
    public ProductManufacturingStatus saveStatus(String barcode, ProcessStateEnum targetStatus) {
/*        ProductManufacturingStatus status=ProductManufacturingStatus.builder()
                                            .barcode(barcode)
                                            .status(targetStatus).build();*/
        ProductManufacturingStatus status = manufacturingStatusRepo.findByBarcode(barcode);
        status.setStatus(targetStatus.toString());
        status.setModifyTime(LocalDateTime.now());
        ProductManufacturingStatus saved = manufacturingStatusRepo.save(status);
        return saved;
    }

    @Override
    public ProductManufacturingStatus saveInitStatus(String barcode, ProcessStateEnum status) {
        ProductManufacturingStatus initStatus=ProductManufacturingStatus.builder()
                                            .barcode(barcode)
                                            .status(status.toString())
                                            .orderNumber("simulated order")
                                            .comment("Init saved")
                                            .createTime(LocalDateTime.now())
                                            .modifyTime(LocalDateTime.now()).build();

        ProductManufacturingStatus saved = manufacturingStatusRepo.save(initStatus);
        return saved;
    }
}//ManufacturingStatusServiceImpl
