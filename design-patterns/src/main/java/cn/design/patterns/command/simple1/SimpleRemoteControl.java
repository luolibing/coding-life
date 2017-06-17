package cn.design.patterns.command.simple1;

/**
 * Created by LuoLiBing on 16/6/3.
 * 调用端,发起指令端
 */
public class SimpleRemoteControl {

    /**
     * 按钮插槽
     */
    private Command slot;

    public SimpleRemoteControl() {}

    public void setCommand(Command command) {
        this.slot = command;
    }

    public void buttonWasPressed() {
        slot.execute();
    }
}
