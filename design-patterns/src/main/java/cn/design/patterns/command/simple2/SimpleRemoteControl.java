package cn.design.patterns.command.simple2;

/**
 * Created by LuoLiBing on 16/6/6.
 */
public class SimpleRemoteControl {

    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;

    public SimpleRemoteControl() {
        onCommands = new Command[2];
        offCommands = new Command[2];

        Command noCommand = new NoCommand();
        for(int i = 0; i < 2; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }

        undoCommand = new NoCommand();
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void undoButtonWasPushed() {
        undoCommand.execute();
    }
}
