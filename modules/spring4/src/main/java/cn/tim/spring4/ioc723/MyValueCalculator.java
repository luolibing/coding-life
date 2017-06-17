package cn.tim.spring4.ioc723;

import cn.tim.spring4.ioc722.CommandManager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/**
 * Created by LuoLiBing on 16/5/30.
 */
@Named
public class MyValueCalculator {

    public String computeValue(String input) {
        return "";
    }

    private CommandManager commandManager;

    @Inject
    public void setCommandManager(Provider<CommandManager> provider) {
        this.commandManager = provider.get();
        commandManager.process("start");
    }
}
