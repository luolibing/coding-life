package cn.tim.state.machine.configuration;

import cn.tim.state.machine.enums.Events;
import cn.tim.state.machine.enums.States;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * User: luolibing
 * Date: 2017/7/22 15:23
 */
@Component
public class StateMachineConfiguration {


    public StateMachine<States, Events> buildMachine(States initState) throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(initState)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.CREATE).target(States.WAIT_SERVE_AUDIT)
                .event(Events.CUSTOMER_SUBMIT)
                .and()
                .withExternal()
                .source(States.CREATE).target(States.WAIT_SERVE_MANAGE_AUDIT)
                .event(Events.SERVE_SUBMIT)
                .and()
                .withExternal()
                .source(States.WAIT_SERVE_AUDIT).target(States.FINISHED)
                .event(Events.SERVE_PASS)
                .and()
                .withExternal()
                .source(States.WAIT_SERVE_AUDIT).target(States.CLOSED)
                .event(Events.SERVE_REJECT)
                .and()

                .withExternal()
                .source(States.WAIT_SERVE_MANAGE_AUDIT).target(States.FINISHED)
                .event(Events.SERVE_MANAGER_PASS)
                .and()

                .withExternal()
                .source(States.WAIT_SERVE_MANAGE_AUDIT).target(States.CLOSED)
                .event(Events.SERVE_MANAGER_REJECT)
                ;

        return builder.build();
    }
}
