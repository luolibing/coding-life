package cn.design.patterns.command.log;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class GoCommand extends SimpleCommand {

    @Override
    public void execute() {
        System.out.println("execute " + getMessage());
    }

    @Override
    public void undo() {
        System.out.println("undo " + getMessage());
    }
}
