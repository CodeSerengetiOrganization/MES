package com.mytech.statemachine;/*
package com.mytech.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

*/
/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2020-11-16
 * @description :
 *//*

@Configuration
//@EnableStateMachine
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<ProcessStateEnum, ProcessEventEnum> {

    @Override
    public void configure(StateMachineStateConfigurer<ProcessStateEnum, ProcessEventEnum> states) throws Exception {
        states
                .withStates()
                .initial(ProcessStateEnum.INIT)
                .states(EnumSet.allOf(ProcessStateEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ProcessStateEnum, ProcessEventEnum> transitions) throws Exception {
*/
/*        transitions.withExternal()
                .source(States.DRAFT).target(States.PUBLISH_TODO)
                .event(Events.ONLINE)
                .and()
                .withExternal()
                .source(States.PUBLISH_TODO).target(States.PUBLISH_DONE)
                .event(Events.PUBLISH)
                .and()
                .withExternal()
                .source(States.PUBLISH_DONE).target(States.DRAFT)
                .event(Events.ROLLBACK);*//*

        transitions.withExternal()
                .source(ProcessStateEnum.INIT).target(ProcessStateEnum.LOAD_OK).event(ProcessEventEnum.LOAD_PASS)
                .and()
                .withExternal()
                .source(ProcessStateEnum.INIT).target(ProcessStateEnum.LOAD_NG).event(ProcessEventEnum.LOAD_FAIL)
                .and()
                .withExternal()
                .source(ProcessStateEnum.LOAD_OK).target(ProcessStateEnum.CUT_OK).event(ProcessEventEnum.CUT_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.LOAD_OK).target(ProcessStateEnum.CUT_NG).event(ProcessEventEnum.CUT_FAIL)
                .and().withExternal()
                .source(ProcessStateEnum.CUT_OK).target(ProcessStateEnum.COAT_OK).event(ProcessEventEnum.COAT_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.CUT_OK).target(ProcessStateEnum.COAT_NG).event(ProcessEventEnum.COAT_FAIL)
                .and().withExternal()
                .source(ProcessStateEnum.COAT_OK).target(ProcessStateEnum.EOL_OK).event(ProcessEventEnum.EOL_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.COAT_OK).target(ProcessStateEnum.EOL_NG).event(ProcessEventEnum.EOL_FAIL)
                .and().withExternal()
                .source(ProcessStateEnum.EOL_OK).target(ProcessStateEnum.PACKAGE_OK).event(ProcessEventEnum.PACKAGE_PASS)
                .and().withExternal()
                .source(ProcessStateEnum.EOL_OK).target(ProcessStateEnum.PACKAGE_NG).event(ProcessEventEnum.PACKAGE_FAIL);

    }


    @Override
    public void configure(StateMachineConfigurationConfigurer<ProcessStateEnum, ProcessEventEnum> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
//                .listener(listener());
    }
    @Bean
    public StateMachineListener<ProcessStateEnum, ProcessEventEnum> listener() {
        return new StateMachineListenerAdapter<ProcessStateEnum, ProcessEventEnum>() {
            @Override
            public void stateChanged(State<ProcessStateEnum, ProcessEventEnum> from, State<ProcessStateEnum, ProcessEventEnum> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
*/
