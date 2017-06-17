package cn.tim.spring4.ioc722;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/5/30.
 */
@Component
public class CommandManager {

    public Object process(Object commandState) {
        Command command = createCommand();
        command.setState(commandState);
        return command.execute();
    }

    @Lookup
    protected Command createCommand() {
        return null;
    }
}
