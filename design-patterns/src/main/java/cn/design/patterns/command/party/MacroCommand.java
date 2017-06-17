package cn.design.patterns.command.party;

import cn.design.patterns.command.simple2.Command;

/**
 * Created by LuoLiBing on 16/6/6.
 * 组合命令
 */
public class MacroCommand implements Command {

    Command[] commands;

    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for(Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {

    }
}
