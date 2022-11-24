package com.mytech.statemachine;

import com.mytech.domain.ProcessEventEnum;
import com.mytech.domain.ProcessStateEnum;
import com.mytech.domain.ProductManufacturingStatus;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-25
 * @description : String indicates barcode, we will use barcode as key to get stateMachineContext from database;
 */
@Component
public class ProcessStateMachinePersist implements StateMachinePersist<ProcessStateEnum,ProcessEventEnum, ProductManufacturingStatus> {

    @Override
    public void write(StateMachineContext<ProcessStateEnum, ProcessEventEnum> context, ProductManufacturingStatus contextObj) throws Exception {
        //maybe here to save state machine context into MySQL database;
    }

    @Override
    public StateMachineContext<ProcessStateEnum, ProcessEventEnum> read(ProductManufacturingStatus contextObj) throws Exception {
            ProcessStateEnum state = ProcessStateEnum.valueOf(contextObj.getStatus());
            String machineId="ProcessStateMachine";
            DefaultStateMachineContext<ProcessStateEnum, ProcessEventEnum> context = new DefaultStateMachineContext<ProcessStateEnum, ProcessEventEnum>
                    (state, null, null, null, null, machineId);
            return context;
    }
}//ProcessStateMachinePersist
