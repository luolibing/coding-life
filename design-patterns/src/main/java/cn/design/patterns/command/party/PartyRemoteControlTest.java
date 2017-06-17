package cn.design.patterns.command.party;

import cn.design.patterns.command.simple2.*;

/**
 * Created by LuoLiBing on 16/6/6.
 */
public class PartyRemoteControlTest {

    public static void main(String[] args) {
        Light light = new Light();
        Cd cd = new Cd();

        // 2 创建出命令,封装了请求,最终的实现方是电器,所以需要传入对应的电器
        Command lightOnCommand = new LightOnCommand(light);
        Command cdOnCommand = new CdOnCommand(cd);

        Command lightOffCommand = new LightOffCommand(light);
        Command cdOffCommand = new CdOffCommand(cd);

        // 3 组合指令
        Command[] partyOn = { lightOnCommand, cdOnCommand };
        Command[] partyOff = { lightOffCommand, cdOffCommand };

        Command partyOnCommand = new MacroCommand(partyOn);
        Command partyOffCommand = new MacroCommand(partyOff);

        // 4 按下按钮
        SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();
        simpleRemoteControl.setCommand(0, partyOnCommand, partyOffCommand);

        simpleRemoteControl.onButtonWasPushed(0);
        System.out.println();
        simpleRemoteControl.offButtonWasPushed(0);
    }
}
