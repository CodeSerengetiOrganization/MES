package com.mytech.statemachine;

import com.mytech.domain.ProcessEventEnum;
import com.mytech.domain.ProcessStateEnum;
import com.mytech.domain.ProductManufacturingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-26
 * @description :
 */
@Configuration
public class PersistConfig {
    @Autowired
    ProcessStateMachinePersist processStateMachinePersist;

    @Bean(name="ManufacturingStatusPersister")
    public StateMachinePersister<ProcessStateEnum, ProcessEventEnum, ProductManufacturingStatus> manufacturingStatusPersister(){
        return new DefaultStateMachinePersister<ProcessStateEnum,ProcessEventEnum, ProductManufacturingStatus>(processStateMachinePersist);
    }//manufacturingStatusPersister
}//PersistConfig
