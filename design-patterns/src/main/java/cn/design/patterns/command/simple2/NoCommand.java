package cn.design.patterns.command.simple2;

/**
 * Created by LuoLiBing on 16/6/6.
 * 空对象给设计模式,使用NullObject或者NoObject来代替null,可以作为一种默认的参数或者返回值
 */
public class NoCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
