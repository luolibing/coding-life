package cn.tim.spring4.ioc722;

import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/5/30.
 */
@Component
public class AnsyncCommand implements Command {

    private String commandState;

    @Override
    public void setState(Object commandState) {
        this.commandState = commandState.toString();
    }

    @Override
    public Object execute() {
        System.out.println(String.format("execute %s command!!!", commandState));
        return commandState + ":command";
    }

}
