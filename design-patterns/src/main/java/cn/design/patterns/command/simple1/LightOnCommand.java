package cn.design.patterns.command.simple1;

/**
 * Created by LuoLiBing on 16/6/3.
 * 具体的电灯开启命令
 */
public class LightOnCommand implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
