package cn.tim.state.machine;

import cn.tim.state.machine.configuration.StateMachineConfiguration;
import cn.tim.state.machine.enums.Events;
import cn.tim.state.machine.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

import static cn.tim.state.machine.enums.Events.CUSTOMER_SUBMIT;
import static cn.tim.state.machine.enums.Events.SERVE_PASS;

/**
 * User: luolibing
 * Date: 2017/7/22 15:19
 */
@SpringBootApplication
public class StateMachineApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StateMachineApplication.class, args);
    }

    @Autowired
    private StateMachineConfiguration configuration;

    @Override
    public void run(String... args) throws Exception {
        StateMachine<States, Events> stateMachine = configuration.buildMachine();
        stateMachine.start();
        stateMachine.sendEvent(CUSTOMER_SUBMIT);
        stateMachine.sendEvent(SERVE_PASS);
        State<States, Events> state = stateMachine.getState();
        System.out.println(state.getId());
    }
}
