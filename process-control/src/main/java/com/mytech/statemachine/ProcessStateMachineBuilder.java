package com.mytech.statemachine;

import com.mytech.domain.ProcessEventEnum;
import com.mytech.domain.ProcessStateEnum;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableWithStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2020-11-20
 * @description :
 */
@Component
@EnableWithStateMachine
public class ProcessStateMachineBuilder {
//    StateMachineFactory
    private static final String MACHINEID="ProcessStateMachine";

    public StateMachine<ProcessStateEnum, ProcessEventEnum> build(BeanFactory factory) throws Exception {

        System.out.println("---Creating Process State Machine---");
        StateMachineBuilder.Builder<ProcessStateEnum, ProcessEventEnum> builder = StateMachineBuilder.builder();
        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINEID)
                .beanFactory(factory);
        builder.configureStates()
                .withStates()
                .initial(ProcessStateEnum.INIT)
                .states(EnumSet.allOf(ProcessStateEnum.class));
        builder.configureTransitions()
                .withExternal()
                .source(ProcessStateEnum.INIT).target(ProcessStateEnum.LOAD_OK).event(ProcessEventEnum.LOAD_PASS)
                .and()
                .withExternal()
                .source(ProcessStateEnum.INIT).target(ProcessStateEnum.LOAD_NG).event(ProcessEventEnum.LOAD_FAIL)
                .and()
                .withExternal()
                .source(ProcessStateEnum.LOAD_OK).target(ProcessStateEnum.COAT_OK).event(ProcessEventEnum.COAT_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.LOAD_OK).target(ProcessStateEnum.COAT_NG).event(ProcessEventEnum.COAT_FAIL)
                .and().withExternal()
                .source(ProcessStateEnum.COAT_OK).target(ProcessStateEnum.CUT_OK).event(ProcessEventEnum.CUT_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.COAT_OK).target(ProcessStateEnum.CUT_NG).event(ProcessEventEnum.CUT_FAIL)
                .and().withExternal()
                .source(ProcessStateEnum.CUT_OK).target(ProcessStateEnum.ASSEMBLE_OK).event(ProcessEventEnum.ASSEMBLE_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.CUT_OK).target(ProcessStateEnum.ASSEMBLE_NG).event(ProcessEventEnum.ASSEMBLE_FAIL)
                .and().withExternal()
                .source(ProcessStateEnum.ASSEMBLE_OK).target(ProcessStateEnum.EOL_OK).event(ProcessEventEnum.EOL_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.ASSEMBLE_OK).target(ProcessStateEnum.EOL_NG).event(ProcessEventEnum.EOL_FAIL)
                .and().withExternal()
                .source(ProcessStateEnum.EOL_OK).target(ProcessStateEnum.PACKAGE_OK).event(ProcessEventEnum.PACKAGE_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.EOL_OK).target(ProcessStateEnum.PACKAGE_NG).event(ProcessEventEnum.PACKAGE_FAIL);
        return builder.build();
    }//build
}//class
