package cn.design.patterns.command.simple1;

/**
 * Created by LuoLiBing on 16/6/3.
 * 命令模式: 将请求封装成对象,以便使用不同的请求\队列或者日志来参数化对象,命令模式也支持可撤销的操作
 */
public class RemoteControlTest {

    public static void main(String[] args) {
        // 创建出一个遥控器
        SimpleRemoteControl control = new SimpleRemoteControl();
        // 一个电灯
        Light light = new Light();
        // 开启电灯命令
        Command lightOn = new LightOnCommand(light);
        // 安装命令
        control.setCommand(lightOn);
        // 按下执行
        control.buttonWasPressed();
    }
}
