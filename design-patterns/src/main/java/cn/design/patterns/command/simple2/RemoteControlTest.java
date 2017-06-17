package cn.design.patterns.command.simple2;

/**
 * Created by LuoLiBing on 16/6/6.
 */
public class RemoteControlTest {

    public static void main(String[] args) {
        // 1 创建出目标对象,电灯与CD,目标操作对象
        Light light = new Light();
        Cd cd = new Cd();

        // 2 创建出命令,封装了请求,最终的实现方是电器,所以需要传入对应的电器
        Command lightOffCommand = new LightOffCommand(light);
        Command lightOnCommand = new LightOnCommand(light);
        Command cdOnCommand = new CdOnCommand(cd);
        Command cdOffCommand = new CdOffCommand(cd);

        // 3 创建出客户端,遥控器,client客户端,遥控器厂商只需要将电器厂商提供的电器操作指令安装到对应的漕即可
        SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();
        simpleRemoteControl.setCommand(0, lightOnCommand, lightOffCommand);
        simpleRemoteControl.setCommand(1, cdOnCommand, cdOffCommand);

        // 4 发起调用
        simpleRemoteControl.onButtonWasPushed(0);
        simpleRemoteControl.undoButtonWasPushed();
//        simpleRemoteControl.offButtonWasPushed(0);
//
//        simpleRemoteControl.onButtonWasPushed(1);
//        simpleRemoteControl.offButtonWasPushed(1);
    }
}
